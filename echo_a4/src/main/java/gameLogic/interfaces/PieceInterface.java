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
public interface PieceInterface {
	public enum Shape { //Piece names and array-based representations
		I1(new int[][] {{0}}, 1), 
		I2(new int[][] {{1}, {0}}, 2), 
		I3(new int[][] {{2}, {1}, {0}}, 3), 
		I4(new int[][] {{3}, {2}, {1}, {0}}, 4), 
		I5(new int[][] {{4}, {3}, {2}, {1}, {0}}, 5), 
		V3(new int[][] {{1, -1}, {0, 2}}, 3), 
		L4(new int[][] {{2, -1}, {1, -1}, {0, 3}}, 4), 
		Z4(new int[][] {{-1, 1, 2}, {3, 0, -1}}, 4), 
		O4(new int[][] {{1, 2}, {0, 3}}, 4), 
		L5(new int[][] {{1, -1, -1, -1}, {0, 2, 3, 4}}, 5), 
		T5(new int[][] {{-1, 2, -1}, {-1, 1, -1}, {4, 0, 3}}, 5), 
		V5(new int[][] {{2, -1, -1}, {1, -1, -1}, {0, 3, 4}}, 5), 
		N(new int[][] {{-1, 0, 1 ,2}, {4, 3, -1, -1}}, 5), 
		Z5(new int[][] {{-1, -1, 2}, {3, 0, 1}, {4, -1, -1}}, 5), 
		T4(new int[][] {{-1, 1, -1}, {3, 0, 2}}, 4), 
		P(new int[][] {{0, 1}, {3, 2}, {4, -1}}, 5), 
		W(new int[][] {{-1, 1, 2}, {3, 0, -1}, {4, -1, -1}}, 5), 
		U(new int[][] {{1, 2}, {0, -1}, {3, 4}}, 5), 
		F(new int[][] {{-1, 1, 2}, {4, 0, -1}, {-1, 3, -1}}, 5), 
		X(new int[][] {{-1, 1, -1}, {4, 0, 2}, {-1, 3, -1}}, 5), 
		Y(new int[][] {{-1, 1, -1, -1}, {4, 0, 2, 3}}, 5);
		
		public final int defaultOrientation[][];
		public final int size;
		
		Shape(int defaultOrientation[][], int size) {
			this.defaultOrientation = defaultOrientation;
			this.size = size;
		}
	}

	String getShape();
	
	int getSize();
	
	void rotate(); //Rotate piece clockwise
	
	void flip(); //Flip piece accross vertical axis

	int getReferencePoint();
	
	void setReferencePoint(int referencePoint);
	
	int[][] getOrientation();

	void setOrientation(int[][] orientation);
	
}
