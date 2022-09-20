/**
 * echo
 * 20201034
 */
package io.interfaces;

import java.util.ArrayList;

import gameLogic.interfaces.*;

/**
 * @author agbod
 *
 */
public interface UI {
	
	PlayerInterface[] getPlayers(int startingPlayer);

	MoveInterface getMove(PlayerInterface player);

	void displayFirstPlayer(PlayerInterface player);

	void displayBoard(BoardInterface board);

	void displayPieces(PlayerInterface player);

	void displayInvalidMove(); //Notify player that move selected was invalid

	void displayPlayerTurn(PlayerInterface player); //Notify player whose turn it is

	void displayNoValidMove(PlayerInterface player); //Notify player that there are no valid moves

	void displayResults(PlayerInterface player1, PlayerInterface player2);

	void hint(ArrayList<MoveInterface> moves);

	void closeScanner();

}
