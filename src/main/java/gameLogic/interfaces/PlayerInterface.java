/**
 * echo
 * 20201034
 */
package gameLogic.interfaces;

import java.util.List;

/**
 * @author agbod
 *
 */
public interface PlayerInterface {
	
	String getName();

	int getPlayerNumber();
		
	int getScore();
	
	List<PieceInterface> getPieces(); //Should return list of all available pieces
	
	void updateHistory(MoveInterface move); //Add a move to the players history
	
	int getMoveNum();
	
}
