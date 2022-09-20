/**
 * echo
 * 20201034
 */
package echo;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.modality.rl.agent.EpsilonGreedy;
import ai.djl.modality.rl.agent.RlAgent;
import ai.djl.modality.rl.agent.QAgent;
import ai.djl.modality.rl.env.RlEnv.Step;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDArrays;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Activation;
import ai.djl.nn.Block;
import ai.djl.nn.Blocks;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.convolutional.Conv2d;
import ai.djl.nn.core.Linear;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.Trainer;
import ai.djl.training.evaluator.Accuracy;
import ai.djl.training.initializer.NormalInitializer;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Adam;
import ai.djl.training.tracker.LinearTracker;
import ai.djl.training.tracker.Tracker;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import model.Board;


class Utils {
	final static String MODEL_PATH = "src/main/resources/model/";
	final static int BATCH_SIZE = 32;
	final static int EPOCH = 10000;
	final static int GAMES_PER_EPOCH = 16;

	static Model model = null;
	static Trainer trainer = null;
	static NDManager manager = null;

	static NDManager getManager() {
		if (manager == null) {
			manager = NDManager.newBaseManager();
		}
		return manager;
	}

    static Model createOrLoadModel() {
    	if (model != null) {
    		return model;
    	} else {
	        model = Model.newInstance("EchoBot");
	        model.setBlock(getBlock());
	        Path path = Paths.get(MODEL_PATH);
	        if (Files.exists(path)) {
	        	try {
		            model.load(path);
	        	} catch (IOException | MalformedModelException | NullPointerException e) {}
	        }
	        return model;
    	}
    }

    private static Block getBlock() { //Dense neural network
        return new SequentialBlock()
                .add(Blocks.batchFlattenBlock())
                .add(Linear
                        .builder()
                        .setUnits(196).build())
                .add(Activation::relu)

                .add(Linear
                        .builder()
                        .setUnits(1024).build())
                .add(Activation::relu)

                .add(Linear
                        .builder()
                        .setUnits(32928).build());
    }

    static RlAgent createAgent(Trainer trainer) {
    	RlAgent agent = new CustomAgent(trainer, 0.9f);
    	Tracker exploreRate =
    	        LinearTracker.builder()
    	                .setBaseValue(0.9f)
    	                .optSlope(-.9f / (EPOCH * GAMES_PER_EPOCH * 7))
    	                .optMinValue(0.01f)
    	                .build();
    	return new EpsilonGreedy(agent, exploreRate);
    }

    static Trainer getTrainer() {
    	if (trainer != null) {
    		return trainer;
    	} else {
	    	model = createOrLoadModel();
	    	trainer = model.newTrainer(setupTrainingConfig());
	    	trainer.initialize(new Shape(1, Board.HEIGHT * Board.WIDTH));
	    	trainer.notifyListeners(listener -> listener.onTrainingBegin(trainer));
	    	return trainer;
    	}
    }

    static NDList chooseAction(NDList observation) { //Get action from model
        if (trainer == null) {
        	getTrainer();
        }
        return trainer.evaluate(observation);
    }

    static void saveModel() {
    	System.out.println("Saving model. ");
    	if (model != null) {
	        Path path = Paths.get(MODEL_PATH);
	        try {
	    		model.save(path, "");
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.exit(0);
	        }
    	}
    }

    static DefaultTrainingConfig setupTrainingConfig() {
        return new DefaultTrainingConfig(Loss.l2Loss())
                .optOptimizer(Adam.builder().optLearningRateTracker(Tracker.fixed(1e-6f)).build())
                .addEvaluator(new Accuracy())
                .addTrainingListeners(TrainingListener.Defaults.basic());
    }
}
