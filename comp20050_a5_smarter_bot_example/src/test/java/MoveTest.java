import model.Gamepiece;
import model.Location;
import model.Move;
import model.Player;
import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {

    @Test
    void getters_shouldReturnCorrectValues_afterMoveIsCreated() {

        out.println("Move test: check that the values given to Move(player,gamepiece,gamepieceName, location) are returned correctly by getters");

        class Helper {
            void runTest(Player givenPlayer, Gamepiece givenGamepiece, String givenGamepieceName, Location givenLocation ) {
                out.printf("\tTesting for Move(%s,%s,%s): ", givenPlayer, givenGamepiece, givenLocation);
                Move move = new Move(givenPlayer, givenGamepiece, givenGamepieceName, givenLocation);
                Player returnedPlayer = move.getPlayer();
                Gamepiece returnedGamepiece = move.getGamepiece();
                Location returnedLocation = move.getLocation();
                out.printf("getPlayer() = %s, getGamepiece() = %s, getLocation() = %s\n", returnedPlayer, returnedGamepiece,returnedLocation);
                assertEquals(givenPlayer, returnedPlayer);
                assertEquals(givenGamepiece, returnedGamepiece);
                assertEquals(givenLocation, returnedLocation);
            }
        }

        {
            Player testPlayer = new TestPlayer(0);
            new Helper().runTest(testPlayer, testPlayer.getGamepieceSet().get("I1"), "I1", new Location(4,9) );
        }

        {
            Player testPlayer = new TestPlayer(1);
            new Helper().runTest(testPlayer, testPlayer.getGamepieceSet().get("V3"), "V3", new Location(0,0) );
        }

    }

    @Test
    void getGamepieceName_shouldReturnCorrectValue_afterMoveIsModifiedWithSetGamepieceName() {

        out.println("Move test: check that the name of the gamepiece given in setGamepieceName(gamepieceName) is returned correctly by getter");

        class Helper {
            void runTest(Player givenPlayer, Gamepiece givenGamepiece, String givenGamepieceName, Location givenLocation ) {
                out.printf("\tTesting for Move(%s,%s,\"\",%s).setGamepieceName(%s): ", givenPlayer, givenGamepiece, givenLocation, givenGamepieceName);
                Move move = new Move(givenPlayer, givenGamepiece, "", givenLocation);
                move.setGamepieceName(givenGamepieceName);
                String returnedGamepieceName = move.getGamepieceName();
                out.printf("getGamepieceName() = %s\n", returnedGamepieceName);
                assertEquals(givenGamepieceName, returnedGamepieceName);
            }
        }

        {
            Player testPlayer = new TestPlayer(0);
            new Helper().runTest(testPlayer, testPlayer.getGamepieceSet().get("I1"), "I1", new Location(4,9) );
        }

        {
            Player testPlayer = new TestPlayer(1);
            new Helper().runTest(testPlayer, testPlayer.getGamepieceSet().get("V3"), "V3", new Location(0,0) );
        }

    }

}
