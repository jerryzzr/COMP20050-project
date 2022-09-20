package ui.graphics;

import model.*;
import ui.UI;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class GraphicsUI implements UI {
    Board board;
    Player[] players;
    Scanner scanner;
    BlokusGame game;
    PipedInputStream in;
    PipedOutputStream out;

    public GraphicsUI(Board board, Player[] players) throws IOException {
        this.board = board;
        this.players = players;
        out = new PipedOutputStream();
        in = new PipedInputStream(out);
        scanner = new Scanner(in);
    }

    @Override
    public void updateDisplay() {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.updateGamepieces(players);
                game.updateBoard(board);
            }
        });
    }

    @Override
    public String getPlayerName(Player player) {

        String name;
        do {
            name = scanner.next();
        } while (name.isEmpty());

        return name;
    }

    public String getGamepieceChoice(Player player) {
        String gamepieceName;
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.setActivePlayerNo(player.getPlayerNo());
                game.displayMessage(
                        "Player " + player.getName() + " click and drag the gamepiece you would like to play");
            }
        });
        gamepieceName = scanner.next();
        return gamepieceName;
    }

    public void displayGamepiece(Gamepiece gamepiece) {
    }

    public String getGamepieceManipulation() {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.displayMessage(
                        "Press 'r' to rotate, 'f' to flip, drag the gamepiece to the board to place it");
            }
        });
        return scanner.next();
    }
    public Location getPlacementLocation() {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        return new Location(x,y);
    }

    @Override
    public void noifyBadMove(Move move) {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.showDialog("Invalid move!");
            }
        });
        updateDisplay();
    }

    public void gamepieceNotFound(String gamepieceName) {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.showDialog("Cannot find '" + gamepieceName + "' among player gamepieces");
            }
        });
    }

    @Override
    public void displayGameOverMessage() {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                Player winner = players[0].playerScore() > players[1].playerScore() ? players[0] : players[1];
                game.showDialog("GAME OVER ! Player "+winner.getName()+" has won.");
                game.displayMessage("Game over!");
            }
        });
    }

    @Override
    public void announcePlayerMakingFirstMove(Player player) {
        game.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setPlayScreen();
                    game.showDialog("Player "+ player.getName() + " goes first!");
                }
            });
    }

    public void setBlokusGame(BlokusGame game) {
        this.game = game;
    }

    public PipedOutputStream getPipe() {
        return out;
    }

    public Board getBoard() {
        return board;
    }
}
