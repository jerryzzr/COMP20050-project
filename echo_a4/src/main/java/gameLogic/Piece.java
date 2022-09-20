/**
 * echo
 * 20201034
 */
package gameLogic;

import java.util.ArrayList;

import gameLogic.interfaces.*;

/**
 * @author agbod
 *
 */
public class Piece implements PieceInterface {

	/**
	 * Class to represent physical object of a piece as a shape with a reference point and orientation
	 */
	final Shape shape;
	private int referencePoint;		
	private int orientation[][];

	public Piece(String pieceName) {
		this.shape = Shape.valueOf(pieceName.toUpperCase());
		this.referencePoint = 0;
		this.orientation = shape.defaultOrientation;
	}

	public Piece(PieceInterface piece) {
		this.shape = Shape.valueOf(piece.getShape());
		this.referencePoint = piece.getReferencePoint();
		int array[][] = piece.getOrientation();
		this.orientation = new int[array.length][];
		for (int i = 0; i < array.length; i++) {
			this.orientation[i] = new int[array[i].length];
		    for (int j = 0; j < array[i].length; j++) {
		    	this.orientation[i][j] = array[i][j];
		    }
		}
	}

	@Override
	public String getShape() {
		return this.shape.name();
	}	

	@Override
	public int getSize() {
		return this.shape.size;
	}

	@Override
	public void rotate() {
		int rows = this.orientation.length;
		int columns = this.orientation[0].length;
		int temp[][] = new int[columns][rows];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				temp[j][i] = this.orientation[rows - (i + 1)][j];
			}
		}
		this.orientation = temp;
	}

	@Override
	public void flip() {
		int rows = this.orientation.length;
		int columns = this.orientation[0].length;
		int temp[][] = new int[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				temp[i][j] = this.orientation[i][columns - (j + 1)];
			}
		}
		this.orientation = temp;
	}

	@Override
	public int getReferencePoint() {
		return this.referencePoint;
	}

	@Override
	public void setReferencePoint(int referencePoint) {
		this.referencePoint = referencePoint;
	}

	@Override
	public int[][] getOrientation() {
		return this.orientation.clone();
	}

	@Override
	public void setOrientation(int[][] orientation) {
		this.orientation = orientation;
	}

}
