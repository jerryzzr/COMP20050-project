/**
 * echo
 * 20201034
 */
package gameLogic.interfaces;

/**
 * @author agbod
 *
 */
public interface MoveInterface {
	
	PlayerInterface getPlayer(); //Return a player object

	PieceInterface getPiece(); //Return a player's piece

	CoordinateInterface getCoordinate(); //Return the selected coordinate

}
