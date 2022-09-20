/**
 * echo
 * 20201034
 */
package gameLogic;

import java.util.ArrayList;
import java.util.List;

import gameLogic.interfaces.*;

/**
 * @author agbod
 *
 */
public class Player implements PlayerInterface {

	/**
	 * Class to represent physical player as a name and a board state, as well as record their pieces and move history
	 */	
	private final String name;
	private final int number;
	private ArrayList<PieceInterface> pieces = new ArrayList<PieceInterface>();
	private ArrayList<MoveInterface> history = new ArrayList<MoveInterface>();
	private int moveNum;

	public Player(String name, int number) { //Initialise a player and give them the full set of pieces
		this.name = name;
		this.number = number;
		for (PieceInterface.Shape shape: PieceInterface.Shape.values()) {
			this.pieces.add(new Piece(shape.name()));
		}
		this.moveNum = 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getPlayerNumber() {
		return this.number;
	}
	
	@Override
	public int getScore() {
		int sum;
		if (pieces.isEmpty()) {
			sum = 15;
			if (history.get(history.size() - 1).getPiece().getSize() == 1) {
				sum += 5;
			}
		} else {
			sum = 0;
			for (PieceInterface piece: this.pieces) {
				sum -= piece.getSize();
			}
		}
		return sum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PieceInterface> getPieces() {
		return (List<PieceInterface>)this.pieces.clone();
	}

	private void playPiece(PieceInterface piece) { //Remove a piece from the players available pieces
		for (PieceInterface p: pieces) {
			if (p.getShape().equals(piece.getShape())) {
				pieces.remove(p);
				break;
			}
		}
	}

	@Override
	public void updateHistory(MoveInterface move) {
		this.history.add(move);
		this.moveNum++;
		playPiece(move.getPiece());
	}

	@Override
	public int getMoveNum() {
		return this.moveNum;
	}

}
