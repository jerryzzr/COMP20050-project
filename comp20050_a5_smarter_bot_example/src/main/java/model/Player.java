package model;

import ui.UI;

import java.util.HashSet;
import java.util.Set;

public abstract class Player {
    private GamepieceSet pieces;
    private String name;
    private int playerNo;
    private boolean lastPlayedI1;

    public Player(int playerNo) {
        this.pieces = new GamepieceSet(playerNo);
        this.playerNo = playerNo;
        this.lastPlayedI1 = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public GamepieceSet getGamepieceSet() {
        return pieces;
    }

    public abstract Move makeMove(Board board);

    public abstract void setUI(UI ui);

    public void setLastPlayedPiece(String pieceName) {
        if (pieceName.equals("I1")) {
            lastPlayedI1 = true;
        } else {
            lastPlayedI1 = false;
        }
    }

    public int playerScore() {
        int score = 0;

        if (pieces.getPieces().size() == 0) {
            score +=15;
            if (lastPlayedI1) {
                score +=5;
            }
        } else {
            for (Gamepiece piece : pieces.getPieces().values() ) {
                score -= piece.getLocations().length;
            }
        }
        return score;
    }

    // Test methods

    @Override
    public String toString() {
        return super.toString()+"(name="+getName()+",playerNo="+getPlayerNo()+",gamepieceSet="+getGamepieceSet()+")";
    }
}
