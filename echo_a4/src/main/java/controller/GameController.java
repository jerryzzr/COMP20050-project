/**
 * echo
 * 20201034
 */
package controller;

import java.util.ArrayList;

import gameLogic.*;
import gameLogic.interfaces.*;
import io.interfaces.*;

/**
 * @author agbod
 *
 */
public class GameController implements Runnable {

	/**
	 * 
	 */
	private volatile Thread blinker;

	private UI ui;
	private BoardInterface board = new Board(); //Initialise board

	public PlayerInterface player1;
	public PlayerInterface player2;

	public boolean gameOver = false;

	private int startingPlayer;

	public GameController(int startingPlayer, UI ui) {
		this.ui = ui;
		this.startingPlayer = startingPlayer;
	}

	public void start() {
        blinker = new Thread(this);
        blinker.start();
    }

    public void stop() {
    	blinker = null;
    	System.exit(0);
    }

    public Thread getThread() {
    	return blinker;
    }

	public void run() { //Run a full game from start to finish
		PlayerInterface[] players = ui.getPlayers(this.startingPlayer);
		this.player1 = players[0];
		this.player2 = players[1];

		ui.displayFirstPlayer(this.player1);
		ui.displayBoard(this.board);

		boolean player1HasMove = board.hasValidMove(player1);
		boolean player2HasMove = board.hasValidMove(player2);
		
		while (player1HasMove || player2HasMove) { //Play until neither player can make a move
			boolean completedMove;

			if (player1HasMove) {
				makeMove(player1);
			} else {
				ui.displayNoValidMove(player1);
			}
			
			if (player2HasMove) {
				makeMove(player2);
			} else {
				ui.displayNoValidMove(player2);
			}

			player1HasMove = board.hasValidMove(player1);
			player2HasMove = board.hasValidMove(player2);
		}
		
		ui.displayResults(player1, player2);
		ui.closeScanner();
		while(!gameOver) {}
	}
	
	private void makeMove(PlayerInterface player) { //Get and make a move
		int invalidMoves = 0;
		boolean completedMove = false;
		ui.displayPlayerTurn(player);

		while (!completedMove) {
			if (invalidMoves > 0) {
				ui.displayInvalidMove();				
			}
			
			try {
				ui.displayPieces(player1);
				ui.displayPieces(player2);
				if (invalidMoves > 0) {
					ui.hint(board.validMoves(player));				
				}
				MoveInterface move = ui.getMove(player);
				board.updateBoard(move);
				player.updateHistory(move);
				ui.displayBoard(board);
				completedMove = true;
			} catch (InvalidMoveException e) {
				invalidMoves++;
			}
		}
	}

}
