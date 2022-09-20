/**
 * echo
 * 20201034
 */
package gameLogicTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.List;

import gameLogic.Player;
import gameLogic.interfaces.PieceInterface;

class TestPlayer {
    @Test
    public void testName() {
        Player player = new Player("John", 0);
        assertEquals("John", player.getName());

        player = new Player("Adam", 0);
        assertEquals("Adam", player.getName());
    }

    @Test
    public void testPlayerNumber() {
        for (int i = -10; i <= 10; i++) {
            Player player = new Player("John", i);
            assertEquals(i, player.getPlayerNumber());
        }
    }

    @Test
    public void testPlayerPieces() {
        Player player = new Player("John", 0);
        List<PieceInterface> pieces = player.getPieces();
        boolean containsAllShapes = true;
        for (PieceInterface.Shape shape: PieceInterface.Shape.values()) {
            boolean check = false;
            for (PieceInterface piece: pieces) {
                if (shape.name().equals(piece.getShape())) {
                    check = true;
                }
            }
            if (!check) {
                containsAllShapes = false;
                break;
            }
        }
        assertTrue(containsAllShapes);
    }

}
