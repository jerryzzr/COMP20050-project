/**
 * echo
 * 20201034
 */
package gameLogic.interfaces;

import java.util.ArrayList;

/**
 * @author agbod
 *
 */
public interface BoardInterface {
	
	final static int BOARD_SIZE = 14;
		
	BoardSquareInterface getSquare(int row, int column);
	
	void updateBoard(MoveInterface move); //Update the board with a given move
	
	ArrayList<MoveInterface> validMoves(PlayerInterface player); //Return all a player's possible moves

	boolean hasValidMove(PlayerInterface player); //Return true if player has any possible moves, false otherwise
	
}
