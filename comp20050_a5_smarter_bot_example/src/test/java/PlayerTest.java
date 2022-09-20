import model.GamepieceSet;
import model.Player;
import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    void getters_shouldReturnCorrectValues_afterPlayerIsCreated() {

        out.println("Player test: check that the values set by Player(playerNo) and setName(name) are returned correctly by getters");

        class Helper {
            void runTest(int givenPlayerNo, String givenName) {
                out.printf("\tTesting for Player(%d): ", givenPlayerNo,givenName);
                Player player = new TestPlayer(givenPlayerNo);
                player.setName(givenName);
                String returnedName  = player.getName();
                int returnedPlayerNo = player.getPlayerNo();
                GamepieceSet returnedGamepieceSet = player.getGamepieceSet();
                out.printf("getPlayerName() = %s, getPlayerNo() = %d, getGamepieceSet() = %s\n", returnedName, returnedPlayerNo,returnedGamepieceSet);
                assertEquals(givenName, returnedName);
                assertEquals(givenPlayerNo, returnedPlayerNo);

                // lightweight checking of GamepieceSet
                assertEquals(21, returnedGamepieceSet.getPieces().size());
            }
        }

        {
            new Helper().runTest(0, "Mary");
            new Helper().runTest(1, "John");

        }

    }

}
