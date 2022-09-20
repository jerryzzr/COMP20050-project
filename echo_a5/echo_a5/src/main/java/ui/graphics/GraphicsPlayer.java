package ui.graphics;

import model.*;
import ui.UI;

public class GraphicsPlayer extends Player {

    private GraphicsUI graphicsUI;

    public GraphicsPlayer(int playerNo) {
        super(playerNo);
    }

    @Override
    public void setUI(UI ui) {
        this.graphicsUI = (GraphicsUI)ui;
    }

    @Override
    public Move makeMove(Board board) {
        String gamepieceName;

        for(;;) {
            gamepieceName = graphicsUI.getGamepieceChoice(this);
            if (null != this.getGamepieceSet().getPieces().get(gamepieceName)) {
                break;
            }
            graphicsUI.gamepieceNotFound(gamepieceName);
        }

        Gamepiece chosenPiece = getGamepieceSet().get(gamepieceName);
        Gamepiece clonedPiece = new Gamepiece(chosenPiece);

        String cmd;
        do {
            graphicsUI.displayGamepiece(clonedPiece);
            cmd = graphicsUI.getGamepieceManipulation();
            switch(cmd) {
                case "r" :
                    clonedPiece.rotateRight();
                    break;
                case "f" :
                    clonedPiece.flipAlongY();
                    break;
            }
        } while (!cmd.contentEquals("p"));
        Location placementLocation = graphicsUI.getPlacementLocation();

        return new Move(this, clonedPiece, gamepieceName, placementLocation);
    }
}
