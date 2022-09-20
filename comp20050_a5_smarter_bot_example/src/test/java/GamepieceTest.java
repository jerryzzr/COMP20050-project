import model.Gamepiece;
import model.Location;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

public class GamepieceTest {


    @Test
    void equals_shouldCorrectlyCompareGamepiece_withGamepieceAndNonGamepieceObjects() {
        out.println("Gamepiece equality: check that equals returns correct values when comparing gamepiece with gamepiece and non-gamepiece objects");
        Gamepiece gamepiece = new Gamepiece(new int[]{0,0,1,0,1,1,2,1,2,2}, 0);
        Gamepiece equalGamepiece = new Gamepiece(new int[]{0,0,1,0,1,1,2,1,2,2}, 0);
        Gamepiece nonEqualGamepiece = new Gamepiece(new int[]{0,0,1,0,1,1,2,1}, 0);

        assertEquals(gamepiece,gamepiece);
        assertEquals(gamepiece,equalGamepiece);
        assertNotEquals(gamepiece, nonEqualGamepiece);
        assertNotEquals(gamepiece, "a string");
    }

    @Test
    void getLocations_shouldReturnCorrectLocaitons_afterGamepieceIsCreated() {

        out.println("Gamepiece test: check that getLocations() returns correct value");

        class Helper {
            void runTest(int[] givenCoordinates, int givenPlayerNo, Location[] expectedLocations) {
                out.printf("\tTesting for Gamepiece(%s,%d):\n", Arrays.toString(givenCoordinates),givenPlayerNo);
                Gamepiece gamepiece = new Gamepiece(givenCoordinates,givenPlayerNo);
                Location[] returnedLocations = gamepiece.getLocations();
                out.printf("\t\texpected Locations() = %s\n",Arrays.toString(expectedLocations) );
                out.printf("\t\tgetLocations() = %s\n",Arrays.toString(returnedLocations) );
                assertArrayEquals(expectedLocations,returnedLocations);
            }
        }

        new Helper().runTest(
                new int[]{},
                1,
                new Location[]{}
        );
        new Helper().runTest(
                new int[]{0,0,1,0,1,1,1,2,1,3},
                0,
                new Location[]{new Location(0,0), new Location(1,0), new Location(1,1), new Location(1,2), new Location(1,3)}
        );
    }

    @Test
    void getPlayerNo_shouldReturnCorrectPlayerNo_afterGamepieceIsCreated() {

        out.println("Gamepiece test: check that getPlayerNo() returns correct value");

        class Helper {
            void runTest(int[] givenCoordinates, int givenPlayerNo) {
                out.printf("\tTesting for Gamepiece(%s,%d): ", Arrays.toString(givenCoordinates),givenPlayerNo);
                Gamepiece gamepiece = new Gamepiece(givenCoordinates,givenPlayerNo);
                int returnedPlayerNo = gamepiece.getPlayerNo();
                out.printf("getPlayerNo() = %d\n", returnedPlayerNo );
                assertEquals(givenPlayerNo,returnedPlayerNo);
            }
        }

        new Helper().runTest( new int[]{0,0,1,0,1,1}, 0);
        new Helper().runTest( new int[]{0,0,1,0,1,1}, 1);
    }

    @Test
    void cloning_shouldReturnTrue_whenComparingLocationsWithSameXY() {

        out.println("Gamepiece test: check that Gamepiece object constructed from another Gamepiece object has the same array of locations and playerNo");
        
        class Helper {
            void runTest(int[] givenCoordinates, int givenPlayerNo) {
                Gamepiece givenGamepiece = new Gamepiece(givenCoordinates,givenPlayerNo);
                Gamepiece createdGamepiece = new Gamepiece(givenGamepiece);

                out.printf("\tTesting for \n\t\tgiven Gamepiece   = %s,\n\t\treturned Gamepiece = %s\n", givenGamepiece, createdGamepiece);
                assertArrayEquals(givenGamepiece.getLocations(), createdGamepiece.getLocations());
                assertEquals(givenGamepiece.getPlayerNo(), createdGamepiece.getPlayerNo());
            }
        }

        new Helper().runTest(new int[]{0,0,1,0,1,1,2,1,2,2}, 0);
        new Helper().runTest(new int[]{0,0}, 1);
    }

    @Test
    void rotateRight_shouldProduceFourDistinctShapes_whenAppliedToWPiece() {

        out.println("Gamepiece test: check that rotating W piece to the right produces four distinct shapes and returns back to the original shape");

        class Helper {
            void runTest(Gamepiece givenGamepiece, Gamepiece expectedGamepiece) {
                Gamepiece rotatedGamepice = new Gamepiece(givenGamepiece);
                rotatedGamepice.rotateRight();
                out.printf("\tTesting for \n\t\tgiven Gamepiece   = %s,\n\t\trotated Gamepiece = %s\n", givenGamepiece, rotatedGamepice);
                assertArrayEquals(expectedGamepiece.getLocations(), rotatedGamepice.getLocations());
                assertEquals(expectedGamepiece.getPlayerNo(), rotatedGamepice.getPlayerNo());
            }
        }

        new Helper().runTest(new Gamepiece(new int[]{0,0,1,0,1,1,2,1,2,2}, 0), new Gamepiece(new int[]{0,0,0,-1,1,-1,1,-2,2,-2}, 0));
        new Helper().runTest(new Gamepiece(new int[]{0,0,0,-1,1,-1,1,-2,2,-2}, 0), new Gamepiece(new int[]{0,0,-1,0,-1,-1,-2,-1,-2,-2}, 0));
        new Helper().runTest(new Gamepiece(new int[]{0,0,-1,0,-1,-1,-2,-1,-2,-2}, 0), new Gamepiece(new int[]{0,0,0,1,-1,1,-1,2,-2,2}, 0));
        new Helper().runTest(new Gamepiece(new int[]{0,0,0,1,-1,1,-1,2,-2,2}, 0),new Gamepiece(new int[]{0,0,1,0,1,1,2,1,2,2}, 0));
    }

    @Test
    void flipAlongY_shouldProduceTwoDistinctShapes_whenAppliedToWPiece() {

        out.println("Gamepiece test: check that flipping W piece along the vertical axis produces two distinct shapes and returns back to the original shape");

        class Helper {
            void runTest(Gamepiece givenGamepiece, Gamepiece expectedGamepiece) {
                Gamepiece rotatedGamepice = new Gamepiece(givenGamepiece);
                rotatedGamepice.flipAlongY();
                out.printf("\tTesting for \n\t\tgiven Gamepiece   = %s,\n\t\trotated Gamepiece = %s\n", givenGamepiece, rotatedGamepice);
                assertArrayEquals(expectedGamepiece.getLocations(), rotatedGamepice.getLocations());
                assertEquals(expectedGamepiece.getPlayerNo(), rotatedGamepice.getPlayerNo());
            }
        }

        new Helper().runTest(new Gamepiece(new int[]{0,0,1,0,1,1,2,1,2,2}, 1), new Gamepiece(new int[]{0,0,-1,0,-1,1,-2,1,-2,2}, 1));
        new Helper().runTest(new Gamepiece(new int[]{0,0,-1,0,-1,1,-2,1,-2,2}, 1), new Gamepiece(new int[]{0,0,1,0,1,1,2,1,2,2}, 1));
    }
}
