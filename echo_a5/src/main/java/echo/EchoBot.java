/**
 * echo
 * 20201034
 */
package echo;

import ai.djl.Model;
import ai.djl.modality.rl.ActionSpace;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDArrays;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.types.Shape;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.Trainer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import model.*;
import SimpleBot.SimpleBotPlayer;

public class EchoBot extends SimpleBotPlayer {
    public final int actionSpaceSize;

    private int moveNo = 0;
    private CustomGamepieceSet referenceSet; 
    private HashMap<String, Integer> pieceIndexes;
    private boolean training;
    private NDManager manager = null;

    public EchoBot(int playerNo) {
        this(playerNo, false);
    }

    EchoBot(int playerNo, boolean training) {
        super(playerNo);
        this.training = training;
        if (!training) {
            this.manager = Utils.getManager();
        }
        this.referenceSet = new CustomGamepieceSet(playerNo);
        this.actionSpaceSize = Board.WIDTH * Board.HEIGHT * referenceSet.getPieces().size() * 4 * 2;
        this.pieceIndexes = new HashMap<String, Integer>();
        Integer i = 0;
        for (String name: referenceSet.getPieces().keySet()) {
            pieceIndexes.put(name, i++);
        }
    }

    NDList getObservation(Board board, NDManager manager) { //Return board state
        float[][] boardArr = new float[Board.WIDTH][Board.HEIGHT];
        for (int y = 0; y < Board.HEIGHT; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                if (board.getOccupyingPlayer(x, y) == getPlayerNo()) {
                    boardArr[y][x] = 1;
                } else if (board.getOccupyingPlayer(x, y) != 0) {
                    boardArr[y][x] = -1;
                } else {
                    boardArr[y][x] = 0;
                }
            }
        }
        NDArray observation = manager.create(boardArr).flatten();
        return new NDList(NDArrays.stack(new NDList(observation)));
    }

    ActionSpace getActionSpace(Board board, NDManager manager) { //Return valid actions
        ActionSpace actionSpace = new ActionSpace();
        Collection<String> gamepieceNames = getGamepieceSet().getPieces().keySet();
        for (String gamepieceName : gamepieceNames) {
            Gamepiece clonedPiece = new Gamepiece(getGamepieceSet().getPieces().get(gamepieceName));
            int pieceNo = pieceIndexes.get(gamepieceName);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    Move move = new Move((Player)this, clonedPiece, gamepieceName, new Location(0,0));
                    for (int x = 0; x < Board.WIDTH; x++) {
                        for (int y = 0; y < Board.HEIGHT; y++) {
                            move.getLocation().setX(x);
                            move.getLocation().setY(y);
                            if ((moveNo == 0 && board.isValidFirstMove(move)) || (moveNo != 0 && board.isValidSubsequentMove(move))) {
                                int index = y + (x * Board.HEIGHT) + (j * Board.WIDTH * Board.HEIGHT) + (i * Board.WIDTH * Board.HEIGHT * 4) + (pieceNo * Board.WIDTH * Board.HEIGHT * 8);
                                actionSpace.add(new NDList(manager.create(index).oneHot(actionSpaceSize)));
                            }
                        }
                    }
                    clonedPiece.rotateRight();
                }
                clonedPiece.flipAlongY();
            }
        }
        return actionSpace;
    }

    @Override
    public Move makeMove(Board board) {
        return makeMove(board, Utils.chooseAction(getObservation(board, this.manager)));
    }

    Move makeMove(Board board, NDList action) {
        Number indexes[] = action.singletonOrThrow().argSort().toArray();
        for (int i = indexes.length - 1; i >= 0; i--) {
            Move move = actionToMove(indexes[i].intValue());
            if (getGamepieceSet().getPieces().keySet().contains(move.getGamepieceName())) {
                if ((moveNo == 0 && board.isValidFirstMove(move)) || (moveNo != 0 && board.isValidSubsequentMove(move))) {
                    moveNo++;
                    return move;
                }
            }
        }
        return null;
    }

    NDArray moveToAction(Move move, NDManager manager) {
        if (move != null) {
            int pieceNo = pieceIndexes.get(move.getGamepieceName());
            int x = move.getLocation().getX();
            int y = move.getLocation().getY();
            int flip = -1;
            int rotate = -1;
            Gamepiece clonedPiece = new Gamepiece(referenceSet.getPieces().get(move.getGamepieceName()));
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    if (haveSameOrientation(clonedPiece, move.getGamepiece())) {
                        flip = i;
                        rotate = j;
                        break;
                    }
                    clonedPiece.rotateRight();
                }
                clonedPiece.flipAlongY();
            }
            int index = y + (x * Board.HEIGHT) + (rotate * Board.WIDTH * Board.HEIGHT) + (flip * Board.WIDTH * Board.HEIGHT * 4) + (pieceNo * Board.WIDTH * Board.HEIGHT * 8);
            return manager.create(index).oneHot(actionSpaceSize);
        } else {
            return manager.zeros(new Shape(actionSpaceSize));
        }
    }

    private boolean haveSameOrientation(Gamepiece a, Gamepiece b) {
        boolean same = true;
        Location[][] pieceLocations = {a.getLocations(), b.getLocations()};
        for (int i = 0; i < pieceLocations[0].length; i++) {
            if (pieceLocations[0][i].getX() == pieceLocations[1][i].getX() || pieceLocations[0][i].getY() == pieceLocations[1][i].getY()) {
                same = false;
            }
        }
        return same;
    }

    Move actionToMove(int index) {
        int pieceNo = index / (Board.WIDTH * Board.HEIGHT * 8);
        index %= (Board.WIDTH * Board.HEIGHT * 8);
        int flip = index / (Board.WIDTH * Board.HEIGHT * 4);
        index %= (Board.WIDTH * Board.HEIGHT * 4);
        int rotate = index / (Board.WIDTH * Board.HEIGHT);
        index %= (Board.WIDTH * Board.HEIGHT);
        int x = index / (Board.HEIGHT);
        index %= (Board.HEIGHT);
        int y = index;

        String pieceName = null;
        Gamepiece piece = null;
        for (String name: pieceIndexes.keySet()) {
            if (pieceIndexes.get(name) == pieceNo) {
                pieceName = name;
                piece = new Gamepiece(referenceSet.get(name));
                break;
            }
        }

        for (int i = 0; i < rotate; i++) {
            piece.rotateRight();
        }

        if (flip == 1) {
            piece.flipAlongY();
        }

        return new Move(this, piece, pieceName, new Location(x, y));
    }
}
