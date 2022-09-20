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
public class Move implements MoveInterface {

	/**
	 * Helper class to represent abstract concept of a move and reduce length of method signatures
	 */
	private PlayerInterface player;
	
	private PieceInterface piece;
	
	private CoordinateInterface coordinate;
		
	public Move(PlayerInterface player, PieceInterface piece, CoordinateInterface coordinate) {
		this.player = player;
		this.piece = new Piece(piece);
		this.coordinate = coordinate;
	}

	@Override
	public PlayerInterface getPlayer() {
		return player;
	}

	@Override
	public PieceInterface getPiece() {
		return piece;
	}

	@Override
	public CoordinateInterface getCoordinate() {
		return coordinate;
	}

}
