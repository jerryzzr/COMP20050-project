/**
 * echo
 * 20201034
 */
package echo;

import ai.djl.ndarray.NDArrays;
import ai.djl.ndarray.NDManager;
import ai.djl.modality.rl.ActionSpace;
import ai.djl.modality.rl.agent.RlAgent;
import ai.djl.modality.rl.env.RlEnv;
import ai.djl.modality.rl.env.RlEnv.Step;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.training.GradientCollector;
import ai.djl.training.Trainer;
import ai.djl.training.listener.TrainingListener.BatchData;
import ai.djl.translate.Batchifier;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class CustomAgent implements RlAgent { //Class that uses variation of Q-learning to train EchoBot

    private final Trainer trainer;
    private final float rewardDiscount;

    public CustomAgent(Trainer trainer, float rewardDiscount) {
        this.trainer = trainer;
        this.rewardDiscount = rewardDiscount;
    }

    @Override
    public NDList chooseAction(RlEnv env, boolean training) {
        return Utils.chooseAction(env.getObservation());
    }

    @Override
    public void trainBatch(Step[] batchSteps) {
        BatchData batchData =
                new BatchData(null, new ConcurrentHashMap<>(), new ConcurrentHashMap<>());

        for (Step step: batchSteps) {
            try (GradientCollector collector = trainer.newGradientCollector()) {
                NDList predictedQ = new NDList(trainer.forward(step.getPreObservation()).singletonOrThrow().mul(step.getAction().singletonOrThrow()));
                NDList targetQ = new NDList(step.getReward());
        
                NDArray lossValue = trainer.getLoss().evaluate(targetQ, predictedQ);
                collector.backward(lossValue);
                batchData.getLabels().put(targetQ.singletonOrThrow().getDevice(), targetQ);
                batchData.getPredictions().put(predictedQ.singletonOrThrow().getDevice(), predictedQ);
            }
        }
    }
}