import echo.EchoBot;
import SimpleBot.SimpleBotPlayer;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import control.BlokusDuoPlay;
import model.*;

import ui.UI;
import ui.graphics.BlokusGame;
import ui.graphics.GraphicsPlayer;
import ui.graphics.GraphicsUI;
import ui.text.TextPlayer;
import ui.text.TextUI;

import java.io.IOException;
import java.util.Random;


public class Sprint5 {

    public static void main(String[] args) throws IOException {

        // Initialise game

        Board board = new Board();
        Player[] players = new Player[Board.startLocations.length];
        UI ui;
        BlokusDuoPlay blokusDuoPlay;
        boolean graphicsUI = false;
        boolean humanX = false;
        int activePlayer = new Random().nextInt(2);

        for (String arg : args) {
            switch (arg) {
                case "-X":
                    activePlayer = 0;
                    break;
                case "-O":
                    activePlayer = 1;
                    break;
                case "-gui":
                    graphicsUI = true;
                    break;
                case "-humanX":
                    humanX = true;
                    break;
            }
        }

        for (int playerNo = 0; playerNo < players.length; playerNo++) {
            SimpleBotPlayer botPlayer = new EchoBot(playerNo);
            botPlayer.setBoard(board);
            players[playerNo] = botPlayer;
        }

        if (graphicsUI) {

            if (humanX) players[0] = new GraphicsPlayer(0);

            ui = new GraphicsUI(board, players);
            for (Player player : players) {
                player.setUI(ui);
            }

            if (SimpleBotPlayer.class.isAssignableFrom(players[0].getClass())) {
                ((SimpleBotPlayer)players[0]).setOpponent(players[1]);
            }

            if (SimpleBotPlayer.class.isAssignableFrom(players[1].getClass())) {
                ((SimpleBotPlayer)players[1]).setOpponent(players[0]);
            }

            blokusDuoPlay = new BlokusDuoPlay(board,players,ui,activePlayer);

            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("BlokusDuo: Sprint 5");
            config.setWindowedMode( BlokusGame.getVirtualWidth(), BlokusGame.getVirtualHeight());
            config.setResizable(false);
            new Lwjgl3Application(new BlokusGame(blokusDuoPlay), config);

        } else {

            if (humanX) players[0] = new TextPlayer(0);

            ui = new TextUI(board, players);
            for (Player player : players) {
                player.setUI(ui);
            }

            if (SimpleBotPlayer.class.isAssignableFrom(players[0].getClass())) {
                ((SimpleBotPlayer)players[0]).setOpponent(players[1]);
            }

            if (SimpleBotPlayer.class.isAssignableFrom(players[1].getClass())) {
                ((SimpleBotPlayer)players[1]).setOpponent(players[0]);
            }

            blokusDuoPlay = new BlokusDuoPlay(board,players,ui,activePlayer);
            blokusDuoPlay.run();
        }

    }
}
