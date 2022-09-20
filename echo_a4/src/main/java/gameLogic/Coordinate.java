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
public class Coordinate implements CoordinateInterface {

	/**
	 * Helper class to store row and column as a single object
	 */	
	private int row, column;
		
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		return column;
	}

}
