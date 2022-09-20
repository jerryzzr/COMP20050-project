/**
 * echo
 * 20201034
 */
package io.graphical.screens;

import java.util.ArrayList;
import java.util.List;

import gameLogic.Coordinate;
import gameLogic.Board;
import gameLogic.interfaces.BoardInterface;
import gameLogic.interfaces.MoveInterface;

import java.lang.InterruptedException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import io.graphical.*;

/**
 * @author agbod
 *
 */
public class GameScreenInputProcessor extends InputAdapter {

    public BlokusDuoGame game;
    public GameScreen gameScreen;

    public GraphicalPiece selectedPiece;
    public GraphicalPiece playedPiece;
    public GraphicalBoard graphicalBoard;
    public int activePlayerNumber;
    public boolean allowHint;
    public ArrayList<MoveInterface> availableMoves;
    public int moveIndex;
    public Sound validMoveSound;
    public Sound invalidMoveSound;

    public GameScreenInputProcessor(BlokusDuoGame game) {
        this.game = game;
        this.gameScreen = game.gameScreen;
        this.selectedPiece = null;
        this.graphicalBoard = this.gameScreen.graphicalBoard;
        this.allowHint = false;
        this.moveIndex = 0;
        this.validMoveSound = Gdx.audio.newSound(Gdx.files.internal("placesound.mp3"));
        this.invalidMoveSound = Gdx.audio.newSound(Gdx.files.internal("placeWrongSound.mp3"));
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean eventHandled = false;

        if (button == Input.Buttons.LEFT) {
            if (this.selectedPiece != null) {
            	this.selectedPiece.reset();
            	this.selectedPiece = null;
            }

            Vector3 coordinates = unprojectScreenCoordinates(Gdx.input.getX(),Gdx.input.getY());

            if (activePlayerNumber == 1) {
            	for (GraphicalPiece piece: this.gameScreen.blackPieces) {
            		if (piece.isHit(coordinates.x, coordinates.y)) {
            			this.gameScreen.setBanner("Press 'f' to flip, or 'r' to rotate the gamepiece. ");
            			this.selectedPiece = piece;
                        this.gameScreen.stopHint();
            			eventHandled = true;
            			break;
            		}
            	}
            } else if (activePlayerNumber == 2) {
            	for (GraphicalPiece piece: this.gameScreen.whitePieces) {
            		if (piece.isHit(coordinates.x, coordinates.y)) {
            			this.gameScreen.setBanner("Press 'f' to flip, or 'r' to rotate the gamepiece. ");
            			this.selectedPiece = piece;
                        this.gameScreen.stopHint();
            			eventHandled = true;
            			break;
            		}
            	}
            }
        }

        return eventHandled;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        boolean eventHandled = false;
        if (this.selectedPiece != null) {
        	if (activePlayerNumber == 1) {
        		this.gameScreen.setBanner("Click and drag a black gamepiece. ");
        	} else if (activePlayerNumber == 2) {
        		this.gameScreen.setBanner("Click and drag a white gamepiece. ");
        	}
        	Vector3 coordinates = unprojectScreenCoordinates(screenX, screenY);
			int boardColumn = graphicalBoard.getBoardColumn(coordinates.x);
        	int boardRow = graphicalBoard.getBoardRow(coordinates.y);
        	if (graphicalBoard.isHit(coordinates.x, coordinates.y)) {
        		try {
        			this.game.pieceQueue.put(this.selectedPiece.piece);
        			this.playedPiece = this.selectedPiece;
        			this.game.coordinateQueue.put(new Coordinate((Board.BOARD_SIZE - 1) - boardRow, boardColumn));
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
        	}

        	this.selectedPiece.reset();
            this.selectedPiece = null;
        }
        return eventHandled;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        boolean eventHandled = false;
        if (null != selectedPiece) {
            Vector3 coord = unprojectScreenCoordinates(screenX,screenY);
            this.selectedPiece.setPosition(coord.x,coord.y);
            eventHandled = true;
        }
        return eventHandled;
    }

    @Override
    public boolean keyDown (int keycode) {
        boolean eventHandled = false;
        if (this.selectedPiece != null) {
            switch(keycode) {
                case Input.Keys.F:
                    this.selectedPiece.flip();
                    eventHandled = true;
                    break;
                case Input.Keys.R:
                    this.selectedPiece.rotate();
                    eventHandled = true;
                    break;
            }
        }
        if (keycode == Input.Keys.H) {
            if (this.allowHint) {
                this.gameScreen.hint(this.availableMoves.get((moveIndex++) % this.availableMoves.size()), activePlayerNumber);                
            }
        }
        return eventHandled;
    }

    Vector3 unprojectScreenCoordinates(int x, int y) {
        Vector3 screenCoordinates = new Vector3(x, y,0);
        Vector3 worldCoordinates = game.camera.unproject(screenCoordinates);
        return worldCoordinates;
    }

    public void setActivePlayerNumber(int playerNumber) {
        this.activePlayerNumber = playerNumber;
        this.availableMoves = null;
        this.allowHint = false;
        this.moveIndex = 0;
    }

    public void updateGraphicalBoard(BoardInterface board) {
        this.validMoveSound.play();
        this.graphicalBoard.updateBoard((Board) board);
        if (this.playedPiece != null) {
        	this.playedPiece.hide();
        	this.playedPiece = null;
        }
    }

    public void hint(ArrayList<MoveInterface> moves) {
    	if (this.availableMoves == null) {
    		this.availableMoves = moves;
    	}
        this.allowHint = true;
    }

}
