/**
 * echo
 * 20201034
 */
package io.graphical;

import java.io.*;

import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import gameLogic.*;
import gameLogic.interfaces.*;
import io.interfaces.*;
import io.text.*;

/**
 * @author agbod
 *
 */
public class GUI implements UI {

	/**
	 * Utility class for graphical IO
	 */
	private PipedOutputStream pipedOutputStream;
	private PipedInputStream pipedInputStream;
	private Scanner scanner;
	private ArrayList<String> playerNames = new ArrayList<String>();
	private BlokusDuoGame game;
	private TextUI textUI = new TextUI();
	private SynchronousQueue<CoordinateInterface> coordinateQueue = new SynchronousQueue<CoordinateInterface>();
	private SynchronousQueue<PieceInterface> pieceQueue = new SynchronousQueue<PieceInterface>();
	private boolean newTurn;
	

	public GUI() throws IOException {
		pipedOutputStream = new PipedOutputStream();
		pipedInputStream = new PipedInputStream(pipedOutputStream);
		initScanner();
	}

    void setGame(BlokusDuoGame game) {
        this.game = game;
    }

    PipedOutputStream getPipedOutputStream() {
        return this.pipedOutputStream;
    }

    SynchronousQueue<CoordinateInterface> getCoordinateQueue() {
    	return this.coordinateQueue;
    }

    SynchronousQueue<PieceInterface> getPieceQueue() {
    	return this.pieceQueue;
    }

	private void initScanner() {
		scanner = new Scanner(pipedInputStream);
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

	private String[] getNames() {
		String player1Name = scanner.nextLine().trim();
		String player2Name = scanner.nextLine().trim();
		while (player1Name.isEmpty() || player2Name.isEmpty() || player1Name == player2Name) {
			if (player1Name.isEmpty()) {
				showMessage("Error! ", "Player 1\'s name cannot be blank. ");
			} else if (player2Name.isEmpty()) {
				showMessage("Error! ", "Player 2\'s name cannot be blank. ");
			} else {
				showMessage("Error! ", "Both players cannot have the same name. ");
			}
			player1Name = scanner.nextLine().trim();
			player2Name = scanner.nextLine().trim();
		}
		return new String[]{player1Name, player2Name};
	}

	@Override
	public MoveInterface getMove(PlayerInterface player) {
		if (this.newTurn) {
			setActivePlayerNumber(player.getPlayerNumber());
			this.newTurn = false;
		}
		PieceInterface piece = getPiece();
		CoordinateInterface coordinate = getCoordinate();
		return new Move(player, piece, coordinate);
	}

	@Override
	public void displayFirstPlayer(PlayerInterface player) {
		game.postRunnable(new Runnable() {
		    @Override
		    public void run() {
		        game.displayFirstPlayer(player.getName() + " goes first. ");
		    }
		});
	}

	@Override
	public void displayBoard(BoardInterface board) {
		updateGraphicalBoard(board);
	}

	@Override
	public void displayPieces(PlayerInterface player) {

	}
	
	@Override
	public void displayInvalidMove() { //Notify player that move selected was invalid
		playInvalidMoveSound();
	}

	@Override
	public void displayPlayerTurn(PlayerInterface player) {
		this.newTurn = true;
		setBanner(player.getName() + "'s turn. ");
	}

	@Override
	public void displayNoValidMove(PlayerInterface player) {

	}

	@Override
	public void displayResults(PlayerInterface player1, PlayerInterface player2) {
		game.postRunnable(new Runnable() {
		    @Override
		    public void run() {
		    	int player1Score = player1.getScore();
		    	int player2Score = player2.getScore();
		    	String message;

		    	if (player1Score > player2Score) {
		    		message = player1.getName() + " finished the game with " + player1Score + " points. \n" + player2.getName() + " finished the game with " + player2Score + " points. \n" + player1.getName() + " wins! ";
		    	} else if (player1Score < player2Score) {
		    		message = player1.getName() + " finished the game with " + player1Score + " points. \n" + player2.getName() + " finished the game with " + player2Score + " points. \n" + player2.getName() + " wins! ";
		    	} else {
		    		message = "This game ended in a draw between " + player1.getName() + " and " + player2.getName() + ". \n" + "Both players had " + player1Score + " points. ";
		    	}
		    	game.activateResultsScreen();
			    game.displayResults(message);
		    }
		});
	}

	@Override
	public void hint(ArrayList<MoveInterface> moves) {
		setBanner("Press 'h' to get a hint. ");
		game.postRunnable(new Runnable() {
		    @Override
		    public void run() {
		        game.hint(moves);
		    }
		});
	}

	@Override
	public void closeScanner() {
		scanner.close();
	}

	private PieceInterface getPiece() { //Get piece a player wants to play
		try {
			return this.pieceQueue.take();
		} catch (InterruptedException e) {
			return null;
		}
	}
		
	private CoordinateInterface getCoordinate() {
		try {
			return this.coordinateQueue.take();
		} catch (InterruptedException e) {
			return null;
		}
	}

	private void showMessage(String title, String message) {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.showMessage(title, message);
            }
        });
   	}

	private void setBanner(String message) {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.setBanner(message);
            }
        });
   	}

	private void setActivePlayerNumber(int playerNumber) {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.setActivePlayerNumber(playerNumber);
            }
        });
   	}

   	private void updateGraphicalBoard(BoardInterface board) {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.updateGraphicalBoard(board);
            }
        });
   	}

   	private void playInvalidMoveSound() {
        game.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.playInvalidMoveSound();
            }
        });
   	}

}
