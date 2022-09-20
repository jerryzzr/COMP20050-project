/**
 * echo
 * 20201034
 */
package gameLogicTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import gameLogic.Coordinate;

class TestCoordinate {
    @Test
    public void testGetRow() {
        for (int i = -10; i <= 10; i++) {
            Coordinate coordinate = new Coordinate(i, 0);
            assertEquals(i, coordinate.getRow());
        }
    }

    @Test
    public void testGetColumn() {
        for (int i = -10; i <= 10; i++) {
            Coordinate coordinate = new Coordinate(0, i);
            assertEquals(i, coordinate.getColumn());
        }
    }

}
