/**
 * echo
 * 20201034
 */
package echo;

import ai.djl.modality.rl.ActionSpace;
import ai.djl.modality.rl.LruReplayBuffer;
import ai.djl.modality.rl.ReplayBuffer;
import ai.djl.modality.rl.agent.RlAgent;
import ai.djl.modality.rl.env.RlEnv;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import model.Board;
import model.Move;
import model.Player;
import SimpleBot.SimpleBotPlayer;

class BlokusDuoEnv implements RlEnv {
	private NDManager manager;
    private ReplayBuffer replayBuffer;
    private ArrayList<BlokusDuoStep> temporalDifferenceBuffer;

    private Board board;
    private EchoBot player1;
    private RandomBot player2;
    private int startingPlayer;
    private int moveNo;

    public BlokusDuoEnv(NDManager manager, int batchSize, int replayBufferSize) {
        this(manager, new LruReplayBuffer(batchSize, replayBufferSize));
    }

    public BlokusDuoEnv(NDManager manager, ReplayBuffer replayBuffer) {
        this.manager = manager;
        this.replayBuffer = replayBuffer;
        this.temporalDifferenceBuffer = new ArrayList<BlokusDuoStep>();
    }

    @Override
    public void reset() {
        this.moveNo = 0;
        this.board = new Board();
        this.player1 = new EchoBot(0, true);
       	this.player2 = new RandomBot(1);
        this.startingPlayer = (new Random().nextInt(2)) + 1;
        this.temporalDifferenceBuffer.clear();
    }

    @Override
    public void close() {
        manager.close();
    }

    @Override
    public NDList getObservation() {
		return player1.getObservation(board, manager);
    }

    @Override
    public ActionSpace getActionSpace() {
		return player1.getActionSpace(board, manager);
    }

    @Override
    public Step step(NDList action, boolean training) {
		NDList preState;
		NDList postState;
		ActionSpace actionSpace;
		NDArray reward;

        preState = player1.getObservation(board, manager);
        Move move = player1.makeMove(board, action);
        board.makeMove(move);
        player1.getGamepieceSet().remove(move.getGamepieceName());
        postState = player1.getObservation(board, manager);
        actionSpace = player1.getActionSpace(board, manager);
        reward = player1.moveToAction(move, manager).mul(player1.playerScore());

    	return new BlokusDuoStep(manager, preState, postState, action, actionSpace, reward);
    }

    @Override
    public float runEnvironment(RlAgent agent, boolean training) {
    	reset();

    	boolean done = false;
        BlokusDuoStep step = null;

    	while (!done) {
            boolean madeStep = false;
            if (startingPlayer == 1) {
                if (moveNo == 0 || board.playerHasMoves(player1)) {
                    NDList action = agent.chooseAction(this, training);
                    step = (BlokusDuoStep) step(action, training);
                    madeStep = true;
                }
                if (moveNo == 0 || board.playerHasMoves(player2)) {
                    Move move = player2.makeMove(board);
                    board.makeMove(move);
                    player2.getGamepieceSet().remove(move.getGamepieceName());
                }
            } else {
                if (moveNo == 0 || board.playerHasMoves(player2)) {
                    Move move = player2.makeMove(board);
                    board.makeMove(move);
                    player2.getGamepieceSet().remove(move.getGamepieceName());
                }
                if (moveNo == 0 || board.playerHasMoves(player1)) {
                    NDList action = agent.chooseAction(this, training);
                    step = (BlokusDuoStep) step(action, training);
                    madeStep = true;
                }
            }

            if (moveNo == 0 || board.playerHasMoves(player1) || board.playerHasMoves(player2)) {
                done = false;
            } else {
                done = true;
                step.setDone(done);
            }

            if (madeStep) {
                temporalDifferenceBuffer.add(0, step);
            }
            moveNo++;
       	}

       	return addTempDiff();
    }

    private float addTempDiff() {
    	float x = 0.25f;
    	float decay = 0.9f;
    	float result = 0;
    	for (BlokusDuoStep step: temporalDifferenceBuffer) {
    		NDArray reward = step.getReward();
            step.setReward(step.getReward().mul((Number) (1 / 100)));
            reward = step.getReward();
            float val = reward.max().getFloat();
    		if (player1.playerScore() > player2.playerScore()) {
        		float temp = (val + (x * decay * val)) / val;
        		step.setReward(reward.mul(temp));
        		result = 1;
    		} else if (player1.playerScore() > player2.playerScore()) {
                float temp = (val + (-1 * x * decay * val)) / val;
                step.setReward(reward.mul(temp));
        		result = -1;
    		}
			replayBuffer.addStep(step);
			decay *= decay;
    	}
    	return result;
    }

    @Override
    public Step[] getBatch() {
        return replayBuffer.getBatch();
    }

    static final class BlokusDuoStep implements RlEnv.Step {

        private NDManager manager;
        private NDList preState;
        private NDList postState;
        private NDList action;
        private ActionSpace actionSpace;
        private NDArray reward;
        private boolean terminal;

        private BlokusDuoStep(NDManager manager, NDList preState, NDList postState, NDList action, ActionSpace actionSpace, NDArray reward) {
            this.manager = manager;
            this.preState = preState;
            this.postState = postState;
            this.action = action;
            this.actionSpace = actionSpace;
            this.reward = reward;
            this.terminal = false;
        }

        @Override
        public NDList getPreObservation() {
            return preState;
        }

        @Override
        public NDList getAction() {
            return action;
        }

        @Override
        public NDList getPostObservation() {
            return postState;
        }

        @Override
        public ActionSpace getPostActionSpace() {
            return actionSpace;
        }

        @Override
        public NDArray getReward() {
            return reward;
        }

        public void setReward(NDArray reward) {
            this.reward = reward;
        }

        @Override
        public boolean isDone() {
            return terminal;
        }

        public void setDone(boolean terminal) {
            this.terminal = terminal;
        }

        @Override
        public void close() {
            manager.close();
        }
    }
}