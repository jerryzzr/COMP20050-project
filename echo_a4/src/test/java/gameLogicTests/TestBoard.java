/**
 * echo
 * 20201034
 */
package gameLogicTests;

import static org.junit.jupiter.api.Assertions.*;

import static java.time.Duration.ofMillis;

import org.junit.jupiter.api.*;

import gameLogic.Board;
import gameLogic.Coordinate;
import gameLogic.InvalidMoveException;
import gameLogic.Move;
import gameLogic.Piece;
import gameLogic.Player;

class TestBoard {
    @Test
    public void testEmptyBoard() {
        Board board = new Board();
        assertTrue(board.getSquare(4, 4).isStartingSquare(1));
        assertTrue(board.getSquare(9, 9).isStartingSquare(2));
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if ((i != 4 && j != 4) && (i != 9 && j != 9)) {
                    assertTrue(board.getSquare(i, j).isEmpty());
                }
            }
        }
    }

    @Test
    public void testUpdateBoard() {
        Board board = new Board();
        Player player1 = new Player("John", 1);
        Player player2 = new Player("Mary", 2);
        Piece piece = new Piece("X");

        assertTrue(board.getSquare(4, 4).isStartingSquare(1));
        assertTrue(board.getSquare(9, 9).isStartingSquare(2));
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if ((i != 4 && j != 4) && (i != 9 && j != 9)) {
                    assertTrue(board.getSquare(i, j).isEmpty());
                }
            }
        }

        Move move = new Move(player1, piece, new Coordinate(5, 4));
        board.updateBoard(move);
        player1.updateHistory(move);
        assertTrue(board.getSquare(4, 4).isOccupied(1));
        assertTrue(board.getSquare(5, 3).isOccupied(1));
        assertTrue(board.getSquare(5, 4).isOccupied(1));
        assertTrue(board.getSquare(5, 5).isOccupied(1));
        assertTrue(board.getSquare(6, 4).isOccupied(1));

        piece = new Piece("L4");
        piece.rotate();
        move = new Move(player2, piece, new Coordinate(9, 9));
        board.updateBoard(move);
        player2.updateHistory(move);
        assertTrue(board.getSquare(9, 9).isOccupied(2));
        assertTrue(board.getSquare(9, 10).isOccupied(2));
        assertTrue(board.getSquare(9, 11).isOccupied(2));
        assertTrue(board.getSquare(10, 9).isOccupied(2));

        piece = new Piece("Z5");
        piece.flip();
        move = new Move(player2, piece, new Coordinate(12, 11));
        board.updateBoard(move);
        player2.updateHistory(move);
        assertTrue(board.getSquare(11, 10).isOccupied(2));
        assertTrue(board.getSquare(12, 10).isOccupied(2));
        assertTrue(board.getSquare(12, 11).isOccupied(2));
        assertTrue(board.getSquare(12, 12).isOccupied(2));
        assertTrue(board.getSquare(13, 12).isOccupied(2));
    }

    public void testUpdateBoardLatency() {
        Board board = new Board();
        Player player1 = new Player("John", 1);
        Player player2 = new Player("Mary", 2);
        Piece piece = new Piece("X");

        Move move = new Move(player1, piece, new Coordinate(5, 4));
        assertTimeout(ofMillis(10), () -> board.updateBoard(move));
        player1.updateHistory(move);
        assertTrue(board.getSquare(4, 4).isOccupied(1));
        assertTrue(board.getSquare(5, 3).isOccupied(1));
        assertTrue(board.getSquare(5, 4).isOccupied(1));
        assertTrue(board.getSquare(5, 5).isOccupied(1));
        assertTrue(board.getSquare(6, 4).isOccupied(1));
    }

    public void testInvalidMoveException() {
        Board board = new Board();
        Player player1 = new Player("John", 1);
        Player player2 = new Player("Mary", 2);
        Piece piece = new Piece("X");
        Move invalidMove = new Move(player1, piece, new Coordinate(9, 9));
        Exception e = assertThrows(InvalidMoveException.class, () -> board.updateBoard(invalidMove));
        assertNotNull(e.getMessage());
    }

}
