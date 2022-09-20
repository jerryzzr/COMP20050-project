/**
 * echo
 * 20201034
 */
package io.graphical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import gameLogic.Coordinate;
import gameLogic.interfaces.BoardInterface;
import gameLogic.interfaces.PieceInterface;
import gameLogic.interfaces.CoordinateInterface;

/**
 * @author agbod
 *
 */
public class GraphicalHintPiece {
    public PieceInterface piece;
    private TextureRegion square;
    private int row;
    private int column;
    private int[][] defaultOrientation;

    public GraphicalHintPiece(PieceInterface piece, TextureRegion square, int row, int column) {
        this.piece = piece;
        this.square = square;
        this.row = row;
        this.column = column;
        this.defaultOrientation = this.piece.getOrientation();
    }

    public void draw(SpriteBatch batch, GraphicalBoard board) {
        CoordinateInterface c = getPieceReferencePoint();
        int[][] orientation = this.piece.getOrientation();
        int referenceRow = c.getRow();
        int referenceColumn = c.getColumn();
        int pieceHeight = orientation.length;
        int pieceWidth = orientation[0].length;
        for (int i = 0; i < pieceHeight; i++) {
            for (int j = 0; j < pieceWidth; j++) {
                if (orientation[i][j] != -1) {
                    batch.draw(square, board.getX((j - referenceColumn) + column), board.getY((i - referenceRow) + row));
                }
            }
        }
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
