/**
 * echo
 * 20201034
 */
package io.graphical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import gameLogic.Coordinate;
import gameLogic.interfaces.PieceInterface;
import gameLogic.interfaces.CoordinateInterface;

/**
 * @author agbod
 *
 */
public class GraphicalPiece {
    public PieceInterface piece;
    private TextureRegion square;
    private float originX; // world X coordinate of the gamepiece's origin
    private float originY; // world Y coordinate of the gamepiece's origin
    public float currentX;
    public float currentY;
    private boolean visible;
    private int[][] defaultOrientation;

    public GraphicalPiece(PieceInterface piece, TextureRegion square, float originX, float originY) {
        this.piece = piece;
        this.square = square;
        this.originX = originX;
        this.originY = originY;
        this.currentX = originX;
        this.currentY = originY;
        this.visible = true;
        this.defaultOrientation = this.piece.getOrientation();
    }

    public void draw(SpriteBatch batch) {
        if (this.visible) {
            CoordinateInterface c = getPieceReferencePoint();
            int[][] orientation = this.piece.getOrientation();
            int pieceHeight = orientation.length;
            int pieceWidth = orientation[0].length;
            for (int i = 0; i < pieceHeight; i++) {
                for (int j = 0; j < pieceWidth; j++) {
                    if (orientation[i][j] != -1) {
                        batch.draw(square, currentX + square.getRegionWidth() * (j - c.getColumn()), currentY + square.getRegionHeight() * (c.getRow() - i));
                    }
                }
            }
        }
    }

    public void flip() {
        this.piece.flip();
    }

    public void rotate() {
        this.piece.rotate();
    }

    public boolean isHit(float x, float y) {
        boolean isHit = false;
        if (this.visible) {
            CoordinateInterface c = getPieceReferencePoint();
            int[][] orientation = this.piece.getOrientation();
            int pieceHeight = orientation.length;
            int pieceWidth = orientation[0].length;
            for (int i = 0; i < pieceHeight; i++) {
                for (int j = 0; j < pieceWidth; j++) {
                    if (orientation[i][j] != -1) {
                        Rectangle rectangle = new Rectangle(currentX + square.getRegionWidth() * (j - c.getColumn()), currentY + square.getRegionHeight() * (c.getRow() - i), square.getRegionWidth(), square.getRegionHeight());
                        if (rectangle.contains(x,y)) {
                            isHit = true;
                        }
                    }
                }
            }
        }
        return isHit;
    }

    public void setPosition(float x, float y) {
        // set new position of the gamepiece, so that the pointer coordinates
        // are in the middle of the "current" square ( Location(0,0) ).
        currentX = x - square.getRegionWidth() * 0.5f;
        currentY = y - square.getRegionHeight() * 0.5f;
    }

    public void setHintPosition(float x, float y) {
        // set new position of the gamepiece, so that the pointer coordinates
        // are in the middle of the "current" square ( Location(0,0) ).
        currentX = x;
        currentY = y;
    }

    public void reset() {
        currentX = originX;
        currentY = originY;
        this.piece.setOrientation(this.defaultOrientation);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void show() {
        this.visible = true;
    }

    public void hide() {
        this.visible = false;
    }

    private CoordinateInterface getPieceReferencePoint() { //Get the row and column of the reference point relative to the rest of the piece
        int orientation[][] = this.piece.getOrientation();
        int pieceHeight = orientation.length;
        int pieceWidth = orientation[0].length;
        int referenceRow = -1;
        int referenceColumn = -1;
        for (int i = 0; i < pieceHeight; i++) {
            for (int j = 0; j < pieceWidth; j++) {
                if (orientation[i][j] == piece.getReferencePoint()) {
                    referenceRow = i;
                    referenceColumn = j;
                    break;
                }
            }
            if (referenceRow != -1 && referenceColumn != -1) {
                break;
            }
        }
        return new Coordinate(referenceRow, referenceColumn);
    }

}
