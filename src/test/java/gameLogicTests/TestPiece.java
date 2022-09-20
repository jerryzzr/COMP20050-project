/**
 * echo
 * 20201034
 */
package gameLogicTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import gameLogic.Piece;

class TestPiece {
    @Test
    public void testPieceConstructors() {
        for (Piece.Shape shape: Piece.Shape.values()) {
            Piece referencePiece = new Piece(shape.name());
            Piece piece = new Piece(referencePiece);
            assertEquals(shape.name(), referencePiece.getShape());
            assertEquals(shape.size, referencePiece.getSize());
            assertTrue(Arrays.deepEquals(shape.defaultOrientation, referencePiece.getOrientation()));

            assertEquals(referencePiece.getShape(), piece.getShape());
            assertEquals(referencePiece.getSize(), piece.getSize());
            assertEquals(referencePiece.getReferencePoint(), piece.getReferencePoint());
            assertTrue(Arrays.deepEquals(referencePiece.getOrientation(), piece.getOrientation()));
        }
    }

    @Test
    public void testRotate() {
        Piece piece = new Piece("T4");
        assertTrue(Arrays.deepEquals(new int[][] {{-1, 1, -1}, {3, 0, 2}}, piece.getOrientation()));
        piece.rotate();
        assertTrue(Arrays.deepEquals(new int[][] {{3, -1}, {0, 1}, {2, -1}}, piece.getOrientation()));

        piece = new Piece("F");
        assertTrue(Arrays.deepEquals(new int[][] {{-1, 1, 2}, {4, 0, -1}, {-1, 3, -1}}, piece.getOrientation()));
        piece.rotate();
        piece.rotate();
        assertTrue(Arrays.deepEquals(new int[][] {{-1, 3, -1}, {-1, 0, 4}, {2, 1, -1}}, piece.getOrientation()));
    }

    @Test
    public void testFlip() {
        Piece piece = new Piece("Y");
        assertTrue(Arrays.deepEquals(new int[][] {{-1, 1, -1, -1}, {4, 0, 2, 3}}, piece.getOrientation()), "Piece orientation check. ");
        piece.flip();
        assertTrue(Arrays.deepEquals(new int[][] {{-1, -1, 1, -1}, {3, 2, 0, 4}}, piece.getOrientation()), "Piece orientation check. ");

        piece = new Piece("F");
        assertTrue(Arrays.deepEquals(new int[][] {{-1, 1, 2}, {4, 0, -1}, {-1, 3, -1}}, piece.getOrientation()), "Piece orientation check. ");
        piece.flip();
        assertTrue(Arrays.deepEquals(new int[][] {{2, 1, -1}, {-1, 0, 4}, {-1, 3, -1}}, piece.getOrientation()), "Piece orientation check. ");
    }

    @Test
    public void testReferencePoint() {
        for (Piece.Shape shape: Piece.Shape.values()) {
            Piece piece = new Piece(shape.name());
            for (int i = -10; i <= 10; i++) {
                piece.setReferencePoint(i);
                assertEquals(i, piece.getReferencePoint());
            }
        }
    }

}
