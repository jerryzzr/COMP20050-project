import model.Board;
import model.Location;
import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

    @Test
    void getXgetY_shouldReturnCorrectValues_afterLocationIsCreated() {

        out.println("Location test: check that the values given to Location(x,y) constructor are returned correctly by getX() and getY()");

        class Helper {
            void runTest(int givenX, int givenY) {
                out.printf("\tTesting for Location(%d,%d): ", givenX, givenY);
                Location location = new Location(givenX, givenY);
                int returnedX = location.getX();
                int returnedY = location.getY();
                out.printf("getX() = %d, getY() = %d)\n", returnedX, returnedY);
                assertEquals(givenX, returnedX);
                assertEquals(givenY, returnedY);
            }
        }

        new Helper().runTest(Integer.MAX_VALUE, Integer.MIN_VALUE);
        new Helper().runTest(Integer.MIN_VALUE, Integer.MAX_VALUE);
        new Helper().runTest(0, 0);
        new Helper().runTest(Board.WIDTH - 3, Board.HEIGHT - 5);
    }

    @Test
    void setXsetYgetXgetY_shouldReturnCorrectValues_afterLocationIsModified() {

        out.println("Location test: check that the values given to Location(x,y) constructor are returned correctly by getX() and getY()");

        class Helper {
            void runTest(int givenX, int givenY) {
                out.printf("\tTesting for Location(%d,%d): ", givenX, givenY);
                Location location = new Location(0, 0);
                location.setX(givenX);
                location.setY(givenY);
                int returnedX = location.getX();
                int returnedY = location.getY();
                out.printf("getX() = %d, getY() = %d)\n", returnedX, returnedY);
                assertEquals(givenX, returnedX);
                assertEquals(givenY, returnedY);
            }
        }

        new Helper().runTest(Integer.MAX_VALUE, Integer.MIN_VALUE);
        new Helper().runTest(Integer.MIN_VALUE, Integer.MAX_VALUE);
        new Helper().runTest(0, 0);
        new Helper().runTest(Board.WIDTH - 3, Board.HEIGHT - 5);
    }

    @Test
    void equals_shouldReturnTrue_whenComparingLocationsWithSameXY() {

        out.println("Location test: check that two Locaiton objects containing same coordinates compare as equal");

        class Helper {
            void runTest(Location a, Object b, boolean expectedEqual) {
                out.printf("\tTesting for Locations %s and %s: expected equality = %b ", a.toString(), String.valueOf(b), expectedEqual);
                boolean returnedEqual = a.equals(b);
                out.printf(", returned equality = %b)\n", returnedEqual);
                assertEquals(expectedEqual, returnedEqual);
            }
        }

        Location locationA = new Location(1, 1);
        Location locationB = new Location(1, 1);
        Location locationC = new Location(-1, -1);
        new Helper().runTest(locationA, locationA, true);
        new Helper().runTest(locationA, locationB, true);
        new Helper().runTest(locationC, locationB, false);
        new Helper().runTest(locationA, locationC, false);
        new Helper().runTest(locationA, null, false);
    }

    @Test
    void cloning_shouldReturnTrue_whenComparingLocationsWithSameXY() {

        out.println("Location test: check that Location object constructed from another location object has the same coordinates");

        class Helper {
            void runTest(int givenX, int givenY) {
                Location givenLocation = new Location(givenX, givenY);
                Location createdLocation = new Location(givenLocation);

                out.printf("\tTesting for Location %s, returned Location = %s)\n", givenLocation, createdLocation);
                assertEquals(givenLocation.getX(), createdLocation.getX());
                assertEquals(givenLocation.getY(), createdLocation.getY());
            }
        }

        new Helper().runTest(1,2);
    }
}
