/**
 * echo
 * 20201034
 */
package controller;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import io.interfaces.*;
import io.graphical.*;
import io.text.*;

import java.io.IOException;

/**
 * @author agbod
 *
 */
public class Main {

	/**
	 * Contains main function to start a game
	 */
	public static int firstPlayer;
	public static boolean useGUI;
	public static GameController gameController;
	public static UI ui;


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parseArgs(args);
		if (useGUI) {
			try {
				ui = new GUI();				
			} catch (IOException e) {
				ui = new TextUI();
			}
		} else {
			ui = new TextUI();
		}
		gameController = new GameController(firstPlayer, ui);
		gameController.start();

		if (useGUI) {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setWindowedMode(BlokusDuoGame.WIDTH, BlokusDuoGame.HEIGHT);
            config.setResizable(false);
            new Lwjgl3Application(new BlokusDuoGame(gameController, ui), config);
        }
	}

	public static void parseArgs(String[] args) {
		firstPlayer = (int)(Math.random() * 2 + 1);
		useGUI = false;

		for (String arg: args) {
			if (arg.equalsIgnoreCase("-x")) {
				firstPlayer = 1;
			} else if (arg.equalsIgnoreCase("-o")) {
				firstPlayer = 2;
			}

			if (arg.equalsIgnoreCase("-gui")) {
				useGUI = true;
			}
		}
	}

}
