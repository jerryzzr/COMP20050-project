package ui.graphics.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import model.Board;

public class GraphicalBoard extends Actor {
    Board board;
    TextureRegion[] playerSquares;
    int squareWidth;
    int squareHeight;

    public GraphicalBoard(Board b, TextureRegion player1Square, TextureRegion player2Square) {
        board = new Board(b);
        playerSquares = new TextureRegion[board.getNumberOfPlayers()];
        playerSquares[0] = player1Square;
        playerSquares[1] = player2Square;
        squareWidth = player1Square.getRegionWidth();
        squareHeight = player1Square.getRegionHeight();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int row = 0; row < board.HEIGHT; row++) {
            for (int col = 0; col < board.WIDTH; col++) {
                if (board.isOccupied(col,row)) {
                    batch.draw(playerSquares[board.getOccupyingPlayer(col,row)],
                               getX()+col*squareWidth,
                               getY()+row*squareHeight);
                }
            }
        }
    }

    public int getSquareHeight() {
        return squareHeight;
    }

    public int getSquareWidth() {
        return squareWidth;
    }

    public void updateBoard(Board board) {
        this.board = new Board(board);
    }
}
