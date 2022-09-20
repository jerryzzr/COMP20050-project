/**
 * echo
 * 20201034
 */
package gameLogic;

import gameLogic.interfaces.*;

/**
 * @author agbod
 *
 */
public class BoardSquare implements BoardSquareInterface {
	private State state;

	public BoardSquare() {
		this.state = State.EMPTY;
	}

	@Override
	public State getState() {
		return this.state;
	}

	@Override
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public boolean isEmpty() {
		return this.state == State.EMPTY;
	}

	@Override
	public boolean isStartingSquare() {
		return this.state == State.PLAYER_1_STARTING_POINT || this.state == State.PLAYER_2_STARTING_POINT;
	}

	@Override
	public boolean isStartingSquare(int playerNumber) {
		if (playerNumber == 1) {
			return this.state == State.PLAYER_1_STARTING_POINT;
		} else if (playerNumber == 2) {
			return this.state == State.PLAYER_2_STARTING_POINT;
		} else {
			return false;
		}
	}

	@Override
	public boolean isOccupied() {
		return this.state == State.PLAYER_1_OCCUPIED || this.state == State.PLAYER_2_OCCUPIED;
	}

	@Override
	public boolean isOccupied(int playerNumber) {
		if (playerNumber == 1) {
			return this.state == State.PLAYER_1_OCCUPIED;
		} else if (playerNumber == 2) {
			return this.state == State.PLAYER_2_OCCUPIED;
		} else {
			return false;
		}
	}

	public void updateBoardSquare(int playerNumber) {
		if (playerNumber == 1) {
			this.state = State.PLAYER_1_OCCUPIED;
		} else if (playerNumber == 2) {
			this.state = State.PLAYER_2_OCCUPIED;
		}
	}

}
