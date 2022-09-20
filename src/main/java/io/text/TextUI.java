/**
 * echo
 * 20201034
 */
package io.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import gameLogic.*;
import gameLogic.interfaces.*;
import io.interfaces.*;

/**
 * @author agbod
 *
 */
public class TextUI implements UI {

	/**
	 * Utility class for console IO
	 */
	private Scanner scanner;
	
	private final char Empty = '.';
	private final char Player1Default = 'X';
	private final char Player2Default = 'O';
	private final char TestPlayerChar = '#';
	private final char Player1StartingPoint = 'x';
	private final char Player2StartingPoint = 'o';

	public TextUI() {
		initScanner();
	}

	private char playerChar(int playerNumber) {
		if (playerNumber == 1) {
			return Player1Default;
		} else if (playerNumber == 2) {
			return Player2Default;
		} else {
			return TestPlayerChar;
		}
	}

	private char boardSquareChar(BoardSquareInterface boardSquare) {
		if (boardSquare.isEmpty()) {
			return Empty;
		} else if (boardSquare.isStartingSquare(1)) {
			return Player1StartingPoint;
		} else if (boardSquare.isStartingSquare(2)) {
			return Player2StartingPoint;
		} else if (boardSquare.isOccupied(1)) {
			return Player1Default;
		} else if (boardSquare.isOccupied(2)) {
			return Player2Default;
		} else {
			return TestPlayerChar;
		}
	}

	private void initScanner() {
		scanner = new Scanner(System.in);
	}
	
	@Override
	public PlayerInterface[] getPlayers(int startingPlayer) {
		String[] playerNames = getNames();
		String player1Name = playerNames[0];
		String player2Name = playerNames[1];
		PlayerInterface player1, player2;
		if (startingPlayer == 1) { //Initialise players
			player1 = new Player(player1Name, 1);
			player2 = new Player(player2Name, 2);
		} else {
			player2 = new Player(player1Name, 1);
			player1 = new Player(player2Name, 2);
		}
		return new PlayerInterface[]{player1, player2};
	}

	@Override
	public MoveInterface getMove(PlayerInterface player) {
		PieceInterface piece = new Piece(getPiece(player));
		displayPiece(player, piece);
		manipulatePiece(player, piece);
		CoordinateInterface coordinate = getCoordinate();
		MoveInterface move = new Move(player, piece, coordinate);
		return move;
	}

	@Override
	public void displayFirstPlayer(PlayerInterface player) {
		System.out.println();		
		System.out.print("Player " + player.getName());
		System.out.println(" (" + playerChar(player.getPlayerNumber()) + ") goes first! ");
	}

	@Override
	public void displayBoard(BoardInterface board) {
		System.out.println();		
		System.out.println("BLOKUS DUO");
		System.out.println();		
		for (int i = 0; i < BoardInterface.BOARD_SIZE; i++) {
			int row = BoardInterface.BOARD_SIZE - (i + 1);
			if (row < 10)
				System.out.print(" " + row + " ");
			else
				System.out.print(row + " ");
			for (int j = 0; j < BoardInterface.BOARD_SIZE; j++) {
				System.out.print(boardSquareChar(board.getSquare(i, j)) + " ");
			}
			System.out.println();
		}
		System.out.println("   0 1 2 3 4 5 6 7 8 9 10111213");
		System.out.println();
		System.out.println();
	}

	private void displayPiece(PlayerInterface player, PieceInterface piece) {
		System.out.println(piece.getShape() + ": ");
		int orientation[][] = piece.getOrientation();
		for (int i = 0; i < orientation.length; i++) {
			for (int j = 0; j < orientation[0].length; j++) {
				if (orientation[i][j] == piece.getReferencePoint()) {
					System.out.print("* ");
				} else if (orientation[i][j] >= 0) {
					System.out.print(playerChar(player.getPlayerNumber()) + " ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("\'*\' is the reference point of the piece. ");
		System.out.println();
	}
	
	// private void printPieceNumbers(PieceInterface piece) { //Print piece as numbers unique to block components of the piece
	// 	System.out.println(piece.getShape() + ": ");
	// 	int orientation[][] = piece.getOrientation();
	// 	for (int i = 0; i < orientation.length; i++) {
	// 		for (int j = 0; j < orientation[0].length; j++) {
	// 			if (orientation[i][j] >= 0) {
	// 				System.out.print(orientation[i][j] + " ");
	// 			} else {
	// 				System.out.print("  ");
	// 			}
	// 		}
	// 		System.out.println();
	// 	}
	// 	System.out.println();
	// 	System.out.println("The reference point of the piece is " + piece.getReferencePoint());
	// 	System.out.println();
	// }

	@Override
	public void displayPieces(PlayerInterface player) {
		System.out.print("Player " + player.getName());
		System.out.print(" (" + playerChar(player.getPlayerNumber()) + ") gamepieces:");
		for (PieceInterface pieces: player.getPieces()) {
			System.out.print(" " + pieces.getShape());
		}
		System.out.println();
	}
	
	private void displayMove(MoveInterface move) {
		PlayerInterface player = move.getPlayer();
		System.out.print("Player " + player.getName());
		System.out.println(" (" + playerChar(player.getPlayerNumber()) + ") can place: ");
			displayPiece(player, move.getPiece());
			System.out.println("on row " + ((BoardInterface.BOARD_SIZE - 1) - move.getCoordinate().getRow()) + ", column " + move.getCoordinate().getColumn());
			System.out.println();
	}

	// public void printMoves(PlayerInterface player, List<MoveInterface> moves) {
	// 	System.out.print("Player " + player.getName());
	// 	System.out.println(" (" + playerChar(player.getPlayerNumber()) + ") moves:");
	// 	for (MoveInterface move: moves) {
	// 		printPiece(player, move.getPiece());
	// 		System.out.println("on row " + ((BoardInterface.BOARD_SIZE - 1) - move.getCoordinate().getRow()) + ", column " + move.getCoordinate().getColumn());
	// 		System.out.println();
	// 	}
	// }

	@Override
	public void displayInvalidMove() { //Notify player that move selected was invalid
		System.out.println("You have entered an invalid move. Try again. ");
		System.out.println();
	}

	@Override
	public void displayPlayerTurn(PlayerInterface player) {
		System.out.println("Player " + player.getName() + "\'s turn. ");
		System.out.println();
	}

	@Override
	public void displayNoValidMove(PlayerInterface player) {
		System.out.println("Player " + player.getName() + " does not have any valid moves. ");
		System.out.println();
	}

	@Override
	public void displayResults(PlayerInterface player1, PlayerInterface player2) {
		displayPieces(player1);
		displayPieces(player2);
		int player1Score = player1.getScore();
		int player2Score = player2.getScore();
		System.out.println();
		System.out.println("GAME OVER! ");
		System.out.println();
		if (player1Score > player2Score) {
			System.out.print(player1.getName() + " finished the game with " + player1Score + " points");
			System.out.println(" and " + player2.getName() + " finished the game with " + player2Score + " points. ");
			System.out.println(player1.getName() + " wins! ");
			System.out.println();
		} else if (player1Score < player2Score) {
			System.out.print(player1.getName() + " finished the game with " + player1Score + " points");
			System.out.println(" and " + player2.getName() + " finished the game with " + player2Score + " points. ");
			System.out.println(player2.getName() + " wins! ");
			System.out.println();
		} else {
			System.out.println("This game ended in a draw between " + player1.getName() + " and " + player2.getName() + ". ");
			System.out.println("Both players had " + player1Score + " points. ");
			System.out.println();
		}
	}

	@Override
	public void hint(ArrayList<MoveInterface> moves) {
		Iterator<MoveInterface> movesIterator = moves.iterator();
		System.out.println("Enter \'h\' for a hint or anything else to enter a move: ");
		String temp = scanner.next();
		while (temp.trim().equalsIgnoreCase("h") && movesIterator.hasNext()) {
			displayMove(movesIterator.next());
			if (movesIterator.hasNext()) {
				System.out.println("Enter \'h\' for another hint or anything else to enter a move: ");
				temp = scanner.next();
			} else {
				System.out.println("There are no more available moves. ");				
			}
		}
	}

	private String[] getNames() {
		System.out.print("Enter the name of Player " + playerChar(1) + ": ");
		String player1Name = scanner.nextLine().trim();
		while (player1Name.isEmpty()) {
			System.out.println("Player " + playerChar(1) + "\'s name cannot be blank. ");
			System.out.print("Enter the name of Player " + playerChar(1) + ": ");
			player1Name = scanner.nextLine().trim();
		}

		System.out.print("Enter the name of Player " + playerChar(2) + ": ");
		String player2Name = scanner.nextLine().trim();
		while (player2Name.isEmpty() || player1Name == player2Name) {
			if (player1Name == player2Name) {
				System.out.println("Player " + playerChar(2) + "\'s name cannot be the same as Player" + playerChar(2) + "\'s name. ");
			} else {
				System.out.println("Player " + playerChar(2) + "\'s name cannot be blank. ");
			}
			System.out.print("Enter the name of Player " + playerChar(2) + ": ");
			player2Name = scanner.nextLine().trim();
		}
		return new String[]{player1Name, player2Name};
	}

	private String getPiece(PlayerInterface player) { //Get piece a player wants to play
		System.out.println();
		System.out.print("Enter the piece you want to play: ");
		String pieceName = scanner.next().trim().toUpperCase();
		ArrayList<String> pieceNames = new ArrayList<String>();
		for (PieceInterface p: player.getPieces()) {
			pieceNames.add(p.getShape());
		}
		while (!pieceNames.contains(pieceName)) {
			System.out.print(pieceName + " is unavailable or not a valid piece. ");
			System.out.print("Enter the piece you want to play: ");
			pieceName = scanner.next().trim().toUpperCase();
		}
		return pieceName;
	}
	
	// public void changeReferencePoint(PieceInterface piece) { //Change the block on a piece that is referenced to place it on the board. Useful for first move
	// 	printPieceNumbers(piece);
	// 	System.out.print("Enter a number between 0 and " + (piece.getSize() - 1));
	// 	System.out.println(" to change the reference point to that number. ");
	// 	System.out.print("Enter anything else to keep it the same: ");
	// 	int referencePoint = -1;
	// 	try {
	// 		referencePoint = scanner.nextInt();
	// 		scanner.nextLine();
	// 	} catch (NoSuchElementException e) {
	// 		referencePoint = piece.getReferencePoint();
	// 		scanner.nextLine();
	// 	}
	// 	if (referencePoint >= 0 && referencePoint <= piece.getSize() - 1 && referencePoint != piece.getReferencePoint()) {
	// 		piece.setReferencePoint(referencePoint);
	// 		System.out.println("The reference point is now " + referencePoint + ". ");
	// 	} else {
	// 		System.out.println("The reference point will remain as " + referencePoint + ". ");
	// 	}
	// 	System.out.println();
	// }

	private void manipulatePiece(PlayerInterface player, PieceInterface piece) { //Rotate the piece
		System.out.println("Enter \'r\' to rotate, \'f\' to flip or \'p\' to place the gamepiece: ");
		String temp = scanner.next();
		while (!temp.trim().equalsIgnoreCase("p")) {
			if (temp.trim().equalsIgnoreCase("r")) {
				piece.rotate();
				displayPiece(player, piece);
			} else if (temp.trim().equalsIgnoreCase("f")) {
				piece.flip();
				displayPiece(player, piece);
			}
			temp = scanner.next();
		}
		System.out.println();
	}
	
	private CoordinateInterface getCoordinate() {
		System.out.print("Enter x and y coordinates on the board: ");
		int row = -1;
		int column = -1;
		try {
			column = scanner.nextInt();
			row = scanner.nextInt();
		} catch (NoSuchElementException e) {
			row = -1;
			column = -1;
		}
		while (row < 0 || row > 13 || column < 0 || column  > 13) {
			try {
				System.out.print("x and y must be integers between 0 and 13: ");
				column = scanner.nextInt();
				row = scanner.nextInt();
			} catch (NoSuchElementException e) {
				row = -1;
				column = -1;
			}
		}
		System.out.println();
		return new Coordinate((BoardInterface.BOARD_SIZE - 1) - row, column);
	}

	@Override
	public void closeScanner() {
		scanner.close();
	}

}
