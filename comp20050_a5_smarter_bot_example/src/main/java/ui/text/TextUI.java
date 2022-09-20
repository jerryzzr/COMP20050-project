package ui.text;

import model.*;

import java.util.Scanner;

public class TextUI implements ui.UI {

    String[] playerSymbol = {"X", "O"};
    String[] startingPositionSymbol = {"x", "o"};
    Board board;
    Player[] players;
    Scanner scanner;

    public TextUI(Board board, Player[] players) {
        this.board = board;
        this.players = players;
        scanner = new Scanner(System.in);
    }

    void updateGamepieceSetDisplay(GamepieceSet set) {
        System.out.print("gamepieces: ");
        for (Object s : set.getPieces().keySet()) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    void updatePlayerDisplay(Player player) {
        System.out.print("Player " + player.getName() + " ("+playerSymbol[player.getPlayerNo()]+") ");
        updateGamepieceSetDisplay(player.getGamepieceSet());
    }
    @Override
    public void updateDisplay() {

        System.out.println();
        System.out.println("BLOKUS DUO");
        System.out.println();
        updateBoardDisplay();
        System.out.println();
        for (Player player : players) {
            updatePlayerDisplay(player);
        }
    }

    private void updateBoardDisplay() {
        for (int y=board.HEIGHT-1; y>=0; y--) {
            System.out.printf("%2d ",y);
            for (int x = 0; x < board.WIDTH; x++) {
                if (board.isOccupied(x,y)) {
                    System.out.print(playerSymbol[board.getOccupyingPlayer(x,y)]+" ");
                }
                else {
                    boolean isStartingLocation = false;
                    int playerNo;
                    for (playerNo = 0; playerNo < Board.startLocations.length; playerNo++) {
                        Location location = Board.startLocations[playerNo];
                        if ( x == location.getX() && y == location.getY() ) {
                            isStartingLocation = true;
                            break;
                        }
                    }
                    if (isStartingLocation) {
                        System.out.print(startingPositionSymbol[playerNo]+" ");
                    } else {
                        System.out.print(". ");
                    }
                }
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int x=0; x<board.WIDTH; x++) {
            System.out.printf("%2d",x);
        }
        System.out.println();
    }

    @Override
    public String getPlayerName(Player player) {
        System.out.printf("Enter the name of Player (%s): ", playerSymbol[player.getPlayerNo()]);
        String name;

        do {
            name = scanner.next();
        } while (name.isEmpty());

        return name;
    }

    public String getGamepieceChoice(Player player) {
        String gamepieceName;

        System.out.print("Player " + player.getName() + " (" + playerSymbol[player.getPlayerNo()]+") enter the name of the gamepiece you would like to play: ");
        gamepieceName = scanner.next();

        return gamepieceName;
    }

    public void displayGamepiece(Gamepiece gamepiece) {
        String[][] field = {
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "}
        };
        int minX,minY,maxX,maxY;
        final int centerX = 5;
        final int centerY = 5;

        for (Location l : gamepiece.getLocations()) {
            if (l.getX()==0 && l.getY() == 0) {
                field[centerX+l.getX()][centerY+l.getY()] = playerSymbol[gamepiece.getPlayerNo()].toLowerCase()+" ";
            } else {
                field[centerX+l.getX()][centerY+l.getY()] = playerSymbol[gamepiece.getPlayerNo()]+" ";
            }

        }

        minX = field.length;
        for (Location l : gamepiece.getLocations()) {
            if ((centerX + l.getX()) < minX) {
                minX = (centerX + l.getX());
            }
        }

        maxX = 0;
        for (Location l : gamepiece.getLocations()) {
            if ((centerX + l.getX()) > maxX) {
                maxX = (centerX + l.getX());
            }
        }

        minY = field[0].length+1;
        for (Location l : gamepiece.getLocations()) {
            if ((centerY + l.getY()) < minY) {
                minY = (centerY + l.getY());
            }
        }

        maxY = 0;
        for (Location l : gamepiece.getLocations()) {
            if ((centerY + l.getY()) > maxY) {
                maxY = (centerY + l.getY());
            }
        }

        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                System.out.print(field[x][y]);
            }
            System.out.println();
        }

    }

    public String getGamepieceManipulation() {
        System.out.println("Enter 'r' to rotate, 'f' to flip, or 'p' to place the gamepiece:");
        return scanner.next();
    }
    public Location getPlacementLocation() {
        System.out.println("Enter x and y coordinates on the board:");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        return new Location(x,y);
    }

    @Override
    public void noifyBadMove(Move move) {
        System.out.println("Invalid move!");
    }

    public void gamepieceNotFound(String gamepieceName) {
        System.out.println("Cannot find '" + gamepieceName + "' among player gamepieces");
    }

    @Override
    public void displayGameOverMessage() {
        Player winner = players[0].playerScore() > players[1].playerScore() ? players[0] : players[1];
        System.out.println();
        System.out.println("GAME OVER !");
        System.out.printf("Player %s won.\n",winner.getName());
        System.out.printf("Final score of player %s is %d.\n",players[0].getName(),players[0].playerScore());
        System.out.printf("Final score of player %s is %d.\n",players[1].getName(),players[1].playerScore());
    }

    @Override
    public void announcePlayerMakingFirstMove(Player player) {
        System.out.println();
        System.out.println("Player "+ player.getName() + " (" + playerSymbol[player.getPlayerNo()] +") goes first!");
    }
}
