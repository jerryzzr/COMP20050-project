/**
 * echo
 * 20201034
 */
package gameLogicTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import gameLogic.Coordinate;
import gameLogic.Move;
import gameLogic.Piece;
import gameLogic.Player;

class TestMove {
    @Test
    public void testPlayer() {
        Move move = new Move(new Player("John", 1), new Piece("I1"), null);
        assertEquals("John", move.getPlayer().getName());
        assertEquals(1, move.getPlayer().getPlayerNumber());

        move = new Move(new Player("Adam", 2), new Piece("I1"), null);
        assertEquals("Adam", move.getPlayer().getName());
        assertEquals(2, move.getPlayer().getPlayerNumber());
    }

    @Test
    public void testPiece() {
        for (Piece.Shape shape: Piece.Shape.values()) {
            Move move = new Move(null, new Piece(shape.name()), null);
            assertEquals(shape.name(), move.getPiece().getShape());
            assertEquals(shape.size, move.getPiece().getSize());
            assertTrue(Arrays.deepEquals(shape.defaultOrientation, move.getPiece().getOrientation()));
        }
    }

    @Test
    public void testGetColumn() {
        for (int i = -10; i <= 10; i++) {
            for (int j = -10; j <= 10; j++) {
                Move move = new Move(null, new Piece("I1"), new Coordinate(i, j));
                assertEquals(i, move.getCoordinate().getRow());
                assertEquals(j, move.getCoordinate().getColumn());
            }
        }
    }

}
