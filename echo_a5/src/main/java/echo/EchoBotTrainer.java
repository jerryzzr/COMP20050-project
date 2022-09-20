/**
 * echo
 * 20201034
 */
package echo;

import ai.djl.Model;
import ai.djl.modality.rl.agent.EpsilonGreedy;
import ai.djl.modality.rl.agent.RlAgent;
import ai.djl.modality.rl.agent.QAgent;
import ai.djl.modality.rl.env.RlEnv.Step;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDArrays;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Block;
import ai.djl.nn.SequentialBlock;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.Trainer;
import ai.djl.training.TrainingResult;
import ai.djl.training.evaluator.Accuracy;
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

class EchoBotTrainer { //Train the bot
    public static void main(String[] args) {
        train();
    }

    public static TrainingResult train() {
        int replayBufferSize = 1024;
        int validationGamesPerEpoch = 1;

        // Create training environment
        BlokusDuoEnv env = new BlokusDuoEnv(Utils.getManager(), Utils.BATCH_SIZE, replayBufferSize);
        Trainer trainer = Utils.getTrainer();

        RlAgent agent = Utils.createAgent(trainer);

        float validationWinRate = 0;
        float trainWinRate = 0;
        for (int i = 0; i < Utils.EPOCH; i++) {
            int trainingWins = 0;
            for (int j = 0; j < Utils.GAMES_PER_EPOCH; j++) {
                System.out.println("Iteration: " + (j + (Utils.GAMES_PER_EPOCH * i)));
                float result = env.runEnvironment(agent, true);
                if (result > 0) {
                    trainingWins++;
                }
                Step[] batchSteps = env.getBatch();
                agent.trainBatch(batchSteps);
                trainer.step();
            }

            trainWinRate = (float) trainingWins / Utils.GAMES_PER_EPOCH;
            System.out.println("Training wins: " + trainWinRate);

            trainer.notifyListeners(listener -> listener.onEpoch(trainer));

            int validationWins = 0;
            for (int j = 0; j < validationGamesPerEpoch; j++) {
                float result = env.runEnvironment(agent, false);
                if (result > 0) {
                    validationWins++;
                }
            }

            validationWinRate = (float) validationWins / validationGamesPerEpoch;
            System.out.println("Validation wins: " + validationWinRate);
            if (i % 128 == 0) {
                Utils.saveModel();
            }
        }

        trainer.notifyListeners(listener -> listener.onTrainingEnd(trainer));

        TrainingResult trainingResult = trainer.getTrainingResult();
        trainingResult.getEvaluations().put("validate_winRate", validationWinRate);
        trainingResult.getEvaluations().put("train_winRate", trainWinRate);

        Utils.saveModel();
        return trainingResult;
    }
}