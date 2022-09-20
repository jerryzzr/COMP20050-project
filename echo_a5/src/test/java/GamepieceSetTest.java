import model.Gamepiece;
import model.GamepieceSet;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GamepieceSetTest {

    @Test
    void getPieces_shouldReturnCorrectPieces_afterGamepieceSetIsCreated() {

        out.println("GamepieceSet test: check that getPieces() returns correct set of Gamepieces after initialisation");

        class Helper {
            void runTest(int givenPlayerNo) {
                out.printf("\tTesting for GamepieceSet(%d): ", givenPlayerNo);
                GamepieceSet gamepieceSet = new GamepieceSet(givenPlayerNo);
                out.println(gamepieceSet);
                Map<String,Gamepiece> returnedPieces = gamepieceSet.getPieces();
                assertEquals(21,returnedPieces.size());
                assertEquals(new Gamepiece(new int[]{0,0}, givenPlayerNo), returnedPieces.get("I1"));
                assertEquals(new Gamepiece(new int[]{0,0,0,1}, givenPlayerNo), returnedPieces.get("I2"));
                assertEquals(new Gamepiece(new int[]{0,0,0,1,1,1,0,-1,1,-1}, givenPlayerNo), returnedPieces.get("U"));
                assertEquals(new Gamepiece(new int[]{0,0,0,1,1,0,2,0,-1,0}, givenPlayerNo), returnedPieces.get("Y"));
            }
        }

        new Helper().runTest(0);
        new Helper().runTest(1);
    }

    @Test
    void get_shouldReturnCorrectValue_afterInitialisation () {

        out.println("GamepieceSet test: check that get() repeatedly returns correct Gamepiece or null after initialisation");

        class Helper {
            void runTest(int givenPlayerNo) {
                out.printf("\tTesting for GamepieceSet(%d): ", givenPlayerNo);
                GamepieceSet gamepieceSet = new GamepieceSet(givenPlayerNo);
                out.println(gamepieceSet);
                assertEquals(new Gamepiece(new int[]{0,0,0,1,0,2}, givenPlayerNo), gamepieceSet.get("I3"));
                assertEquals(null, gamepieceSet.get("F1"));
                assertEquals(new Gamepiece(new int[]{0,0,0,1,0,2}, givenPlayerNo), gamepieceSet.get("I3"));
            }
        }

        new Helper().runTest(0);
        new Helper().runTest(1);
    }

    @Test
    void remove_shouldReturnCorrectValues_beforeAndAfterRemoval () {

        out.println("GamepieceSet test: check that remove() returns correct Gamepiece and removes it from the set");

        class Helper {
            void runTest(int givenPlayerNo) {
                out.printf("\tTesting for GamepieceSet(%d): ", givenPlayerNo);
                GamepieceSet gamepieceSet = new GamepieceSet(givenPlayerNo);
                out.println(gamepieceSet);
                assertEquals(new Gamepiece(new int[]{0,0,0,1,1,0}, givenPlayerNo), gamepieceSet.remove("V3"));
                assertEquals(null, gamepieceSet.remove("F1"));
                assertEquals(null, gamepieceSet.remove("V3"));
            }
        }

        new Helper().runTest(0);
        new Helper().runTest(1);
    }

}
