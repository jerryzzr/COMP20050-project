package ui.graphics.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import model.Gamepiece;
import model.Location;
import ui.graphics.BlokusGame;

import java.io.PrintStream;

public class GraphicalGamePiece extends Group {
    Gamepiece gamepiece;
    Gamepiece defaultOrientation;
    TextureRegion square;
    String name;
    Array<Image> squares = new Array<>();
    float defaultX;
    float defaultY;
    BlokusGame blokusGame;
    int halfSquareHeight;
    int halfSquareWidth;
    int playerNo;

    public GraphicalGamePiece(String name, Gamepiece gamepiece, TextureRegion square, int playerNo, float defaultX, float defaultY, BlokusGame blokusGame) {
        this.name = name;
        this.defaultOrientation = new Gamepiece(gamepiece);
        this.square = square;
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        this.blokusGame = blokusGame;
        this.halfSquareHeight = square.getRegionHeight() / 2;
        this.halfSquareWidth = square.getRegionWidth() / 2;
        this.playerNo = playerNo;
        for(Location l : gamepiece.getLocations()) {
            Image image = new Image(square);
            image.setPosition(l.getX()*square.getRegionWidth(),l.getY()*square.getRegionHeight());
            addActor(image);
            squares.add(image);
        }
        setDefaultPosition();
        setDefaultOrientation();
        addListener(new GamePieceListener());
    }

    public void setDefaultOrientation() {
        gamepiece = new Gamepiece(defaultOrientation);
        updateSquarePositions();
    }

    public void setDefaultPosition() {
        setPosition(defaultX,defaultY);
    }

    public void flipAlongY() {
        gamepiece.flipAlongY();
        updateSquarePositions();
    }

    public void rotateRight() {
        gamepiece.rotateRight();
        updateSquarePositions();
    }

    private void updateSquarePositions() {
        for(int i = 0; i < squares.size; i++) {
            Location l = gamepiece.getLocations()[i];
            Image image = squares.get(i);
            image.setPosition(l.getX()*square.getRegionWidth(),l.getY()*square.getRegionHeight());
        }
    }

    public String getGamepieceName() {
        return name;
    }

    public BlokusGame getBlokusGame() { return blokusGame; }

    public float getDefaultX() {
        return defaultX;
    }

    public float getDefaultY() {
        return defaultY;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    class GamePieceListener extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            boolean result = false;

            GraphicalGamePiece piece = (GraphicalGamePiece) event.getListenerActor();
            BlokusGame blokusGame = piece.getBlokusGame();
            PrintStream pipe = blokusGame.getPipe();

            // check if the gamepiece belongs to the active player
            if ( (button == 0) && (piece.getPlayerNo() == blokusGame.getActivePlayerNo()) ) {

                // Inform game control about the player's choice of the gamepiece
                pipe.println(piece.getGamepieceName());

                // Send keyboard events to the clicked gamepiece
                Stage stage = piece.getStage();
                stage.setKeyboardFocus(piece);

                //bring selected gamepiece to the front of the stage
                setZIndex(getParent().getChildren().size - 1);

                result = true;
            }
            return result;
        }

        @Override
        public void touchDragged (InputEvent event, float x, float y, int pointer) {
            GraphicalGamePiece piece = (GraphicalGamePiece) event.getListenerActor();
            piece.setPosition(event.getStageX() - piece.halfSquareWidth, event.getStageY() - piece.halfSquareHeight);
        }

        @Override
        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            GraphicalGamePiece piece = (GraphicalGamePiece) event.getListenerActor();
            BlokusGame blokusGame = piece.getBlokusGame();
            PrintStream pipe = blokusGame.getPipe();
            GraphicalBoard graphicalBoard = blokusGame.getGraphicalBoard();
            Rectangle boardBounds = new Rectangle(graphicalBoard.getX(),graphicalBoard.getY(),graphicalBoard.getWidth(),graphicalBoard.getHeight());

            if (boardBounds.contains(event.getStageX(),event.getStageY())) {
                int col = (int) (event.getStageX()-boardBounds.getX()) / graphicalBoard.getSquareWidth();
                int row = (int) (event.getStageY()-boardBounds.getY()) / graphicalBoard.getSquareHeight();
                pipe.printf("p %d %d\n",col,row);
            } else {
                // Give game control impossible location for the move (outside the graphicalBoard)
                pipe.printf("p -1 -1\n");
            }
            Stage stage = piece.getStage();
            stage.unfocus(piece);
        }

        @Override
        public boolean keyDown(InputEvent event, int keyCode) {
            boolean eventHandled = false;
            GraphicalGamePiece piece = (GraphicalGamePiece) event.getListenerActor();
            BlokusGame blokusGame = piece.getBlokusGame();
            PrintStream pipe = blokusGame.getPipe();

            switch (keyCode) {
                case Input.Keys.F :
                    piece.flipAlongY();
                    pipe.println('f');
                    eventHandled = true;
                    break;
                case Input.Keys.R :
                    piece.rotateRight();
                    pipe.println('r');
                    eventHandled = true;
                    break;
            }
            return eventHandled;
        }
    }



}
