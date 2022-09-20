/**
 * echo
 * 20201034
 */
package gameLogic.interfaces;

/**
 * @author agbod
 *
 */
public interface BoardSquareInterface {
	public enum State {
		EMPTY, 
		PLAYER_1_STARTING_POINT, 
		PLAYER_2_STARTING_POINT, 
		PLAYER_1_OCCUPIED, 
		PLAYER_2_OCCUPIED;
	}

	State getState();

	void setState(State state);

	boolean isEmpty();

	boolean isStartingSquare();

	boolean isStartingSquare(int playerNumber);

	boolean isOccupied();

	boolean isOccupied(int playerNumber);

	void updateBoardSquare(int playerNumber);

}
