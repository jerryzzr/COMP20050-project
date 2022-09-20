/**
 * echo
 * 20201034
 */
package gameTests;

import static org.junit.jupiter.api.Assertions.*;

import static java.time.Duration.ofMillis;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import controller.Main;
import gameLogic.*;
import gameLogic.interfaces.*;
import io.text.*;

class TestBlokusDuo {
    @Test
    public void testCommandLineOptions() {
        String args1[] = {"-X"};
        String args2[] = {"-o", "-gui", "ignore", " this "};
        String args3[] = {"no", "options"};

        ArrayList<Integer> results = new ArrayList<Integer>();
        results.add(1);
        results.add(2); 
        
        Main.parseArgs(args1);
        assertEquals(1, Main.firstPlayer, "Command line options check. ");
        assertFalse(Main.useGUI, "Command line options check. ");
        Main.parseArgs(args2);
        assertEquals(2, Main.firstPlayer, "Command line options check. ");
        assertTrue(Main.useGUI, "Command line options check. ");
        Main.parseArgs(args3);
        assertTrue(results.contains(Main.firstPlayer), "No command line options check. ");
        assertFalse(Main.useGUI, "Command line options check. ");
    }

}
