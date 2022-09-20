/**
 * echo
 * 20201034
 */
package gameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gameLogic.interfaces.*;

/**
 * @author agbod
 *
 */
public class Board implements BoardInterface {

	/**
	 * Class to represent the physical object of the board as a 2D-array of squares/states
	 */	
	private BoardSquare board[][] = new BoardSquare[BOARD_SIZE][BOARD_SIZE];
	
	public Board() { //Initialise board
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				this.board[i][j] = new BoardSquare();
			}
		}
		this.board[4][4].setState(BoardSquare.State.PLAYER_1_STARTING_POINT);
		this.board[9][9].setState(BoardSquare.State.PLAYER_2_STARTING_POINT);
	}

	@Override
	public BoardSquareInterface getSquare(int row, int column) {
		return this.board[row][column];
	}
	
	@Override
	public void updateBoard(MoveInterface move) {
		if (isValidMove(move)) {
			int orientation[][] = move.getPiece().getOrientation();
			int pieceHeight = orientation.length;
			int pieceWidth = orientation[0].length;
			CoordinateInterface referencePoint = getPieceReferencePoint(move.getPiece());
			int referenceRow = referencePoint.getRow();
			int referenceColumn = referencePoint.getColumn();
			int row =  move.getCoordinate().getRow();
			int column = move.getCoordinate().getColumn();
			int playerNumber = move.getPlayer().getPlayerNumber();
			for (int i = 0; i < pieceHeight; i++) {
				for (int j = 0; j < pieceWidth; j++) {
					if (orientation[i][j] >= 0) {
						int boardRow = (i - referenceRow) + row;
						int boardColumn = (j - referenceColumn) + column;
						this.board[boardRow][boardColumn].updateBoardSquare(playerNumber);
					}
				}
			}
		} else {
			throw new InvalidMoveException("Invalid move. ");
		}
	}
	
	private boolean isWithinBoundaryCheck(MoveInterface move) { //Check that piece will be placed within board
		int orientation[][] = move.getPiece().getOrientation();
		int pieceHeight = orientation.length;
		int pieceWidth = orientation[0].length;
		CoordinateInterface referencePoint = getPieceReferencePoint(move.getPiece());
		int referenceRow = referencePoint.getRow();
		int referenceColumn = referencePoint.getColumn();
		int row =  move.getCoordinate().getRow();
		int column = move.getCoordinate().getColumn();
		boolean check = row - referenceRow >= 0 && row + (pieceHeight - (referenceRow + 1)) <= 13 && column - referenceColumn >= 0 && column + (pieceWidth - (referenceColumn + 1)) <= 13;
		return check;
	}
	
	private boolean noOccupiedSquareCheck(MoveInterface move) { //Check that piece will not overlap other pieces
		int orientation[][] = move.getPiece().getOrientation();
		int pieceHeight = orientation.length;
		int pieceWidth = orientation[0].length;
		CoordinateInterface referencePoint = getPieceReferencePoint(move.getPiece());
		int referenceRow = referencePoint.getRow();
		int referenceColumn = referencePoint.getColumn();
		int row =  move.getCoordinate().getRow();
		int column = move.getCoordinate().getColumn();
		boolean check = true;
		for (int i = 0; i < pieceHeight; i++) {
			for (int j = 0; j < pieceWidth; j++) {
				if (orientation[i][j] >= 0) {
					int boardRow = (i - referenceRow) + row;
					int boardColumn = (j - referenceColumn) + column;
					if (!this.board[boardRow][boardColumn].isEmpty()) {
						check = false;
						break;
					}
				}
			}
			if (!check) {
				break;
			}
		}
		return check;
	}

	private boolean noAdjacentSquareCheck(MoveInterface move) { //Check that that there will be no adjacent piece of the same colour
		int orientation[][] = move.getPiece().getOrientation();
		int pieceHeight = orientation.length;
		int pieceWidth = orientation[0].length;
		CoordinateInterface referencePoint = getPieceReferencePoint(move.getPiece());
		int referenceRow = referencePoint.getRow();
		int referenceColumn = referencePoint.getColumn();
		int row =  move.getCoordinate().getRow();
		int column = move.getCoordinate().getColumn();
		int playerNumber = move.getPlayer().getPlayerNumber();
		boolean check = true;
		for (int i = 0; i < pieceHeight; i++) {
			for (int j = 0; j < pieceWidth; j++) {
				if (orientation[i][j] >= 0) {
					int boardRow = (i - referenceRow) + row;
					int boardColumn = (j - referenceColumn) + column;
					if (boardRow - 1 >= 0 && this.board[boardRow - 1][boardColumn].isOccupied(playerNumber)) {
						check = false;
						break;
					}
					if (boardRow + 1 <= 13 && this.board[boardRow + 1][boardColumn].isOccupied(playerNumber)) {
						check = false;
						break;
					}
					if (boardColumn - 1 >= 0 && this.board[boardRow][boardColumn - 1].isOccupied(playerNumber)) {
						check = false;
						break;
					}
					if (boardColumn + 1 <= 13 && this.board[boardRow][boardColumn + 1].isOccupied(playerNumber)) {
						check = false;
						break;
					}
				}
			}
			if (!check) {
				break;
			}
		}
		return check;
	}

	private boolean isOnStartingSquare(MoveInterface move) {
		int orientation[][] = move.getPiece().getOrientation();
		int pieceHeight = orientation.length;
		int pieceWidth = orientation[0].length;
		CoordinateInterface referencePoint = getPieceReferencePoint(move.getPiece());
		int referenceRow = referencePoint.getRow();
		int referenceColumn = referencePoint.getColumn();
		int row =  move.getCoordinate().getRow();
		int column = move.getCoordinate().getColumn();
		int playerNumber = move.getPlayer().getPlayerNumber();
		boolean check = false;
		for (int i = 0; i < pieceHeight; i++) {
			for (int j = 0; j < pieceWidth; j++) {
				if (orientation[i][j] >= 0) {
					int boardRow = (i - referenceRow) + row;
					int boardColumn = (j - referenceColumn) + column;
					if (this.board[boardRow][boardColumn].isStartingSquare(playerNumber)) {
						check = true;
					}
				}
			}
		}
		return check;
	}

	private boolean hasCornerSquareCheck(MoveInterface move) { //Check that piece will be touching the corner of another piece of the same colour
		int orientation[][] = move.getPiece().getOrientation();
		int pieceHeight = orientation.length;
		int pieceWidth = orientation[0].length;
		CoordinateInterface referencePoint = getPieceReferencePoint(move.getPiece());
		int referenceRow = referencePoint.getRow();
		int referenceColumn = referencePoint.getColumn();
		int row =  move.getCoordinate().getRow();
		int column = move.getCoordinate().getColumn();
		int playerNumber = move.getPlayer().getPlayerNumber();
		boolean check = false;
		for (int i = 0; i < pieceHeight; i++) {
			for (int j = 0; j < pieceWidth; j++) {
				if (orientation[i][j] >= 0) {
					int boardRow = (i - referenceRow) + row;
					int boardColumn = (j - referenceColumn) + column;
					if (boardRow - 1 >= 0 && boardColumn -1 >= 0 && this.board[boardRow - 1][boardColumn - 1].isOccupied(playerNumber)) {
						check = true;
						break;
					}
					if (boardRow - 1 >= 0 && boardColumn + 1 <= 13 && this.board[boardRow - 1][boardColumn + 1].isOccupied(playerNumber)) {
						check = true;
						break;
					}
					if (boardRow + 1 <= 13 && boardColumn - 1 >= 0 && this.board[boardRow + 1][boardColumn - 1].isOccupied(playerNumber)) {
						check = true;
						break;
					}
					if (boardRow + 1 <= 13 && boardColumn + 1 <= 13 && this.board[boardRow + 1][boardColumn + 1].isOccupied(playerNumber)) {
						check = true;
						break;
					}
				}
			}
			if (check) {
				break;
			}
		}
		return check;
	}

	private boolean isValidMove(MoveInterface move) { //Checks that all conditions are satisfied
		if (move.getPlayer().getMoveNum() == 0) { //Checks for first move when there is no other piece of the same colour on the board
			return isWithinBoundaryCheck(move) && isOnStartingSquare(move);
		} else {
			return isWithinBoundaryCheck(move) && noOccupiedSquareCheck(move) && noAdjacentSquareCheck(move) && hasCornerSquareCheck(move);
		}
	}

	private CoordinateInterface getPieceReferencePoint(PieceInterface piece) { //Get the row and column of the reference point relative to the rest of the piece
		int orientation[][] = piece.getOrientation();
		int pieceHeight = orientation.length;
		int pieceWidth = orientation[0].length;
		int referenceRow = -1;
		int referenceColumn = -1;
		for (int i = 0; i < pieceHeight; i++) {
			for (int j = 0; j < pieceWidth; j++) {
				if (orientation[i][j] == piece.getReferencePoint()) {
					referenceRow = i;
					referenceColumn = j;
					break;
				}
			}
			if (referenceRow != -1 && referenceColumn != -1) {
				break;
			}
		}
		return new Coordinate(referenceRow, referenceColumn);
	}

	@Override
	public ArrayList<MoveInterface> validMoves(PlayerInterface player) {
		ArrayList<MoveInterface> availableMoves = new ArrayList<MoveInterface>();
		for (PieceInterface p: player.getPieces()) {
			PieceInterface piece = new Piece(p.getShape());
			for (int i = 0; i < 4; i++) {
				piece.rotate();
				for (int j = 0; j < 2; j++) {
					piece.flip();
					for (int k = 0; k < Board.BOARD_SIZE; k++) {
						for (int l = 0; l < Board.BOARD_SIZE; l++) {
							MoveInterface move = new Move(player, piece, new Coordinate(k, l));
							if (isValidMove(move) && !containsMove(availableMoves, move)) {
								availableMoves.add(move);
							}
						}
					}
				}
			}
		}
		return availableMoves;
	}

	private boolean containsMove(List<MoveInterface> moves, MoveInterface move) {
		boolean present = false;
		for (MoveInterface m: moves) {
			PieceInterface p = m.getPiece();
			int r = m.getCoordinate().getRow();
			int c = m.getCoordinate().getColumn();
			if (r == move.getCoordinate().getRow() && c == move.getCoordinate().getColumn() && haveSameOrientation(p.getOrientation(), move.getPiece().getOrientation())) {
				present = true;
				break;
			}
		}
		return present;
	}

	private boolean haveSameOrientation(int[][] orientation1, int[][] orientation2) {
		int height1 = orientation1.length;
		int height2 = orientation2.length;
		int width1 = orientation1[0].length;
		int width2 = orientation2[0].length;
		boolean check = true;
		if (height1 == height2 && width1 == width2) {
			for (int i = 0; i < height1; i++) {
				for (int j = 0; j < width1; j++) {
					if ((orientation1[i][j] == -1 && orientation2[i][j] != -1) || (orientation1[i][j] != -1 && orientation2[i][j] == -1)) {
						check = false;
						break;
					}
				}
				if (!check) {
					break;
				}
			}
		} else {
			check = false;
		}
		return check;
	}

	@Override
	public boolean hasValidMove(PlayerInterface player) {
		return !this.validMoves(player).isEmpty();
	}

}
