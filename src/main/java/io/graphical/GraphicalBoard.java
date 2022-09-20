/**
 * echo
 * 20201034
 */
package io.graphical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gameLogic.Board;
import gameLogic.interfaces.MoveInterface;

/**
 * @author agbod
 *
 */
public class GraphicalBoard {
    float boardX;
    float boardY;
    float boardWidth;
    float boardHeight;
    float cellWidth;
    float cellHeight;
    TextureRegion player1Square;
    TextureRegion player2Square;
    Board board;

    public GraphicalBoard(Board board, TextureRegion player1Square, TextureRegion player2Square, float boardX, float boardY, float boardWidth, float boardHeight) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.player1Square = player1Square;
        this.player2Square = player2Square;
        this.cellHeight = boardHeight / Board.BOARD_SIZE;
        this.cellWidth = boardWidth / Board.BOARD_SIZE;
        this.board = new Board();
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

    public void draw(SpriteBatch batch) {
        for (int y = 0; y < Board.BOARD_SIZE; y++) {
            for (int x = 0; x < Board.BOARD_SIZE; x++) {
                if (board.getSquare(y, x).isOccupied(1)) {
                    batch.draw(this.player1Square, getX(x), getY(y));
                } else if (board.getSquare(y, x).isOccupied(2)) {
                    batch.draw(this.player2Square, getX(x), getY(y));
                }
            }
        }
    }

    public int getBoardRow(float y) {
        int result = -1;
        if ((boardY < y) && ((boardY + boardHeight) > y)) {
            result = (int)((y - boardY) / cellHeight);
        }
        return result;
    }

    public int getBoardColumn(float x) {
        int result = -1;
        if ((boardX < x) && ((boardX + boardWidth) > x)) {
            result = (int)((x - boardX) / cellWidth);
        }
        return result;
    }

    public float getX(int column) {
        float result = -1f;
        if (column >= 0 && column < Board.BOARD_SIZE) {
            result = boardX + (column * cellWidth);
        }
        return result;
    }

    public float getY(int row) {
        float result = -1f;
        if (row >= 0 && row < Board.BOARD_SIZE) {
            result = boardY + (((Board.BOARD_SIZE - 1) - row) * cellHeight);
        }
        return result;
    }

    public boolean isHit(float x, float y) {
        return  (getBoardColumn(x) != -1) && (getBoardRow(y) != -1);
    }

    public boolean isOccupied(int row, int column) {
        return board.getSquare(row, column).isOccupied();
    }
}
