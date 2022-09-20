/**
 * echo
 * 20201034
 */
package gameLogicTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import gameLogic.BoardSquare;

class TestBoardSquare {
    @Test
    public void testBoardSquareConstructor() {
        BoardSquare boardSquare = new BoardSquare();
        assertEquals(BoardSquare.State.EMPTY, boardSquare.getState());
    }

    @Test
    public void testState() {
        BoardSquare boardSquare = new BoardSquare();
        for (BoardSquare.State state: BoardSquare.State.values()) {
            boardSquare.setState(state);
            assertEquals(state, boardSquare.getState());
        }
    }

    @Test
    public void testStartingSquare() {
        BoardSquare boardSquare = new BoardSquare();
        for (BoardSquare.State state: BoardSquare.State.values()) {
            boardSquare.setState(state);
            if (state == BoardSquare.State.PLAYER_1_STARTING_POINT || state == BoardSquare.State.PLAYER_2_STARTING_POINT) {
                assertTrue(boardSquare.isStartingSquare());
            } else {
                assertFalse(boardSquare.isStartingSquare());
            }
        }
    }

    @Test
    public void testPlayerStartingSquare() {
        BoardSquare boardSquare = new BoardSquare();
        for (BoardSquare.State state: BoardSquare.State.values()) {
            boardSquare.setState(state);
            for (int i = -10; i <= 10; i++) {
                if (i == 1) {
                    if (state == BoardSquare.State.PLAYER_1_STARTING_POINT) {
                        assertTrue(boardSquare.isStartingSquare(i));
                    } else {
                        assertFalse(boardSquare.isStartingSquare(i));
                    }
                } else if (i == 2) {
                    if (state == BoardSquare.State.PLAYER_2_STARTING_POINT) {
                        assertTrue(boardSquare.isStartingSquare(i));
                    } else {
                        assertFalse(boardSquare.isStartingSquare(i));
                    }
                } else {
                    assertFalse(boardSquare.isStartingSquare(i));
                }
            }
        }
    }

    @Test
    public void testOccupied() {
        BoardSquare boardSquare = new BoardSquare();
        for (BoardSquare.State state: BoardSquare.State.values()) {
            boardSquare.setState(state);
            if (state == BoardSquare.State.PLAYER_1_OCCUPIED || state == BoardSquare.State.PLAYER_2_OCCUPIED) {
                assertTrue(boardSquare.isOccupied());
            } else {
                assertFalse(boardSquare.isOccupied());
            }
        }
    }

    @Test
    public void testPlayerOccupied() {
        BoardSquare boardSquare = new BoardSquare();
        for (BoardSquare.State state: BoardSquare.State.values()) {
            boardSquare.setState(state);
            for (int i = -10; i <= 10; i++) {
                if (i == 1) {
                    if (state == BoardSquare.State.PLAYER_1_OCCUPIED) {
                        assertTrue(boardSquare.isOccupied(i));
                    } else {
                        assertFalse(boardSquare.isOccupied(i));
                    }
                } else if (i == 2) {
                    if (state == BoardSquare.State.PLAYER_2_OCCUPIED) {
                        assertTrue(boardSquare.isOccupied(i));
                    } else {
                        assertFalse(boardSquare.isOccupied(i));
                    }
                } else {
                    assertFalse(boardSquare.isOccupied(i));
                }
            }
        }
    }

    @Test
    public void testUpdateBoardSquare() {
        BoardSquare boardSquare = new BoardSquare();
        for (int i = -10; i <= 10; i++) {
            BoardSquare.State state = boardSquare.getState();
            boardSquare.updateBoardSquare(i);
            if (i == 1 || i == 2) {
                assertTrue(boardSquare.isOccupied(i));
            } else {
                assertEquals(state, boardSquare.getState());
            }
        }
    }

}
