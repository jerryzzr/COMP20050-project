package ui.text;

import model.*;
import ui.UI;

public class TextPlayer extends Player {
    TextUI textUi;

    public TextPlayer(int playerNo) {
        super(playerNo);
    }

    @Override
    public void setUI(UI ui) {
        this.textUi = (TextUI)ui;
    }

    @Override
    public Move makeMove(Board board) {
        String gamepieceName;

        for(;;) {
            gamepieceName = textUi.getGamepieceChoice(this);
            if (null != this.getGamepieceSet().getPieces().get(gamepieceName)) {
                break;
            }
            textUi.gamepieceNotFound(gamepieceName);
        }

        Gamepiece chosenPiece = getGamepieceSet().get(gamepieceName);
        Gamepiece clonedPiece = new Gamepiece(chosenPiece);

        String cmd;
        do {
            textUi.displayGamepiece(clonedPiece);
            cmd = textUi.getGamepieceManipulation();
            switch(cmd) {
                case "r" :
                    clonedPiece.rotateRight();
                    break;
                case "f" :
                    clonedPiece.flipAlongY();
                    break;
            }
        } while (!cmd.contentEquals("p"));
        Location placementLocation = textUi.getPlacementLocation();

        return new Move(this, clonedPiece, gamepieceName, placementLocation);
    }
}
