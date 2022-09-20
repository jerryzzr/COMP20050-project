package control;

import model.Board;
import model.Move;
import model.Player;
import ui.UI;
import ui.graphics.GraphicsUI;

public class BlokusDuoPlay implements Runnable {

    private Board board;
    private Player[] players;
    private UI ui;
    private int activePlayer;

    public BlokusDuoPlay(Board board, Player[] players, UI ui, int activePlayer) {
        this.board = board;
        this.players = players;
        this.ui = ui;
        this.activePlayer = activePlayer;
    }

    @Override
    public void run() {

        // Get player names

        for (Player player : players) {
            player.setName(ui.getPlayerName(player));
        }

        // Gameplay

        int gameTurn = 0;
        boolean otherPlayerSkipped = false;

        ui.announcePlayerMakingFirstMove(players[activePlayer]);

        for(;;) {
            Move move;
            boolean isValidMove;

            ui.updateDisplay();

            /* check if the player still has some possible moves,
             * terminate game if the other player did not have moves on the previous move either
             */
            if ( (gameTurn >=2) && (! board.playerHasMoves(players[activePlayer]))) {
                if (otherPlayerSkipped) {
                    break;
                } else {
                    otherPlayerSkipped = true;
                }
            } else {

                // Active player has moves let's play!
                otherPlayerSkipped = false;

                do {
                    move = players[activePlayer].makeMove(board);

                    if (gameTurn < 2) {
                        isValidMove = board.isValidFirstMove(move);
                    } else {
                        isValidMove = board.isValidSubsequentMove(move);
                    }

                    if (isValidMove) {
                        board.makeMove(move);
                    } else {
                        ui.noifyBadMove(move);
                    }
                } while (!isValidMove);

                players[activePlayer].getGamepieceSet().remove(move.getGamepieceName());
            }

            gameTurn = gameTurn + 1;
            activePlayer = (activePlayer + 1) % players.length;

        }

        // Game is finished

        ui.displayGameOverMessage();

    }

    public UI getUI() {
        return ui;
    }

    public Player[] getPlayers() {
        return players;
    }
}
