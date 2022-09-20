package model;

import java.util.Collection;
import java.util.StringTokenizer;

public class Board {

     public final static int HEIGHT = 14;
     public final static int WIDTH = 14;

     public final static Location[] startLocations = {new Location(4,9), new Location(9,4)};
     private boolean occupied[][] = new boolean[WIDTH][HEIGHT];
     private int occupyingPlayer[][] = new int[WIDTH][HEIGHT];

     public Board() {
          clear();
     }

     public Board(Board board) {
          for (int y = 0; y < HEIGHT; y++) {
               for (int x = 0; x < WIDTH; x++) {
                    this.occupied[x][y] = board.isOccupied(x,y);
                    this.occupyingPlayer[x][y] = board.getOccupyingPlayer(x,y);
               }
          }
     }

     public boolean isOccupied(int x, int y) {
          return occupied[x][y];
     }

     public int getOccupyingPlayer(int x, int y) {
          return occupyingPlayer[x][y];
     }

     public int getNumberOfPlayers() {
          return startLocations.length;
     }

     public void clear() {
          for (int y = 0; y < HEIGHT; y++) {
               for (int x = 0; x < WIDTH; x++) {
                    occupied[x][y] = false;
                    occupyingPlayer[x][y] = 0;
               }
          }
     }

     private boolean isOutsideBoard(Gamepiece gamepiece, int x, int y) {
          boolean result = false;
          for (Location l : gamepiece.locations) {
               if (((l.getX()+x) < 0) || ((l.getX()+x) >= WIDTH) || ((l.getY()+y)<0) || ((l.getY()+y)>=HEIGHT)) {
                    result = true;
               }
          }
          return result;
     }

     private boolean overlapsOtherPieces(Gamepiece gamepiece, int x, int y) {
          boolean result = false;
          for (Location l : gamepiece.locations) {
               if (occupied[l.getX()+x][l.getY()+y]) {
                    result = true;
               }
          }
          return result;
     }

     private boolean coversStartPosition(Gamepiece gamepiece, int x, int y, Location start) {
          boolean result = false;
          for (Location l : gamepiece.locations) {
               if (l.getX()+x == start.getX() && l.getY()+y == start.getY()) {
                    result = true;
               }
          }
          return result;
     }

     /* This function performs boundary checking and returns true only if the target square is within
      ** board, is occupied, AND contains the specified playerNo
      */
     private boolean boardSquareContains(int x, int y, int playerNo) {
          if ( x < 0 || x >= occupied.length || y < 0 || y >= occupied[0].length) return false;
          if (! occupied[x][y]) return false;
          if (occupyingPlayer[x][y] != playerNo) return false;
          return true;
     }

     /*
      ** Checks if at least one square at a side of the specified board location is occupied by a gamepiece
      ** with the specified playerNo
      */
     private boolean boardSquareTouchesAtASide(int x, int y, int playerNo) {
          if ( boardSquareContains(x-1,y,playerNo)
                  || boardSquareContains(x+1,y,playerNo)
                  || boardSquareContains(x,y-1,playerNo)
                  || boardSquareContains(x,y+1,playerNo) )
               return true;
          else
               return false;
     }

     /*
      ** Checks if at least one square at a corner of the specified board location is occupied by a gamepiece
      ** with the specified playerNo
      */
     private boolean boardSquareTouchesAtACorner(int x, int y, int playerNo) {
          if ( boardSquareContains(x-1,y-1,playerNo)
                  || boardSquareContains(x+1,y-1,playerNo)
                  || boardSquareContains(x-1,y+1,playerNo)
                  || boardSquareContains(x+1,y+1,playerNo) )
               return true;
          else
               return false;
     }

     private boolean touchesSamePlayerPiecesOnlyAtCorners(Gamepiece gamepiece, int x, int y) {
          boolean atLeastOneSquareTouchesAtASide = false;
          boolean atLeastOneSquareTouchesAtCorner = false;
          int playerNo = gamepiece.getPlayerNo();
          for (Location l : gamepiece.locations) {
               if (boardSquareTouchesAtACorner(l.getX() + x, l.getY() + y, playerNo)) {
                    atLeastOneSquareTouchesAtCorner = true;
               }
               if (boardSquareTouchesAtASide(l.getX() + x, l.getY() + y, playerNo)) {
                    atLeastOneSquareTouchesAtASide = true;
               }
          }
          return  atLeastOneSquareTouchesAtCorner && (! atLeastOneSquareTouchesAtASide);
     }

     public boolean isValidFirstMove(Move move) {
          Gamepiece gamepiece = move.getGamepiece();
          int x = move.getLocation().getX();
          int y = move.getLocation().getY();
          int playerNo = move.getPlayer().getPlayerNo();

          if ( isOutsideBoard(gamepiece,x,y) ) return false;
          if ( overlapsOtherPieces(gamepiece,x,y) ) return false;
          if ( ! coversStartPosition(gamepiece,x,y,Board.startLocations[playerNo]) ) return false;

          return true;
     }

     public boolean isValidSubsequentMove(Move move) {
          Gamepiece gamepiece = move.getGamepiece();
          int x = move.getLocation().getX();
          int y = move.getLocation().getY();

          if ( isOutsideBoard(gamepiece,x,y) ) return false;
          if ( overlapsOtherPieces(gamepiece,x,y) ) return false;
          if ( ! touchesSamePlayerPiecesOnlyAtCorners(gamepiece,x,y) ) return false;

          return true;
     }

     public void makeMove(Move move) {
        Player player = move.getPlayer();
        Gamepiece gamepiece = move.getGamepiece();
        int x = move.getLocation().getX();
        int y = move.getLocation().getY();

        for (Location l : gamepiece.locations) {
           occupied[l.getX() + x][l.getY() + y] = true;
           occupyingPlayer[l.getX() + x][l.getY() + y] = player.getPlayerNo();
        }
     }

     public boolean playerHasMoves(Player player) {
          boolean result = false;
          Collection<Gamepiece> gamepieces = player.getGamepieceSet().getPieces().values();
          for (Gamepiece gamepiece : gamepieces) {
               if (gamepieceCanBePlaced(gamepiece,player)) {
                    result = true;
                    break;
               }
          }
          return result;
     }

     private boolean gamepieceCanBePlaced(Gamepiece gamepiece,Player player) {
          boolean result = false;
          Gamepiece clonedPiece = new Gamepiece(gamepiece);
          for (int i = 0; i < 4 ; i++) {
               if (gamepieceOrientationCanBePlaced(clonedPiece,player)) {
                    result = true;
                    break;
               }
               clonedPiece.rotateRight();
          }
          clonedPiece.flipAlongY();
          for (int i = 0; i < 4 ; i++) {
               if (gamepieceOrientationCanBePlaced(clonedPiece,player)) {
                    result = true;
                    break;
               }
               clonedPiece.rotateRight();
          }
          return result;
     }

     private boolean gamepieceOrientationCanBePlaced(Gamepiece piece, Player player) {
          boolean result = false;
          Move move = new Move(player,piece,"",new Location(0,0));
          for (int x = 0; x < WIDTH; x++) {
               for (int y = 0; y < HEIGHT; y++) {
                    move.getLocation().setX(x);
                    move.getLocation().setY(y);
                    if (isValidSubsequentMove(move)) {
                         result = true;
                         break;
                    }
               }
          }
          return result;
     }

     // Test methods

     public Board(String initialState) {
          this();
          StringTokenizer tokenizer = new StringTokenizer(initialState);
          for(int y=(HEIGHT-1); y>=0; y--) {
               for (int x=0; x<WIDTH; x++) {
                    switch(tokenizer.nextToken()) {
                         case "X":
                              occupied[x][y]=true;
                              occupyingPlayer[x][y]=0;
                              break;
                         case "O":
                              occupied[x][y]=true;
                              occupyingPlayer[x][y]=1;
                              break;
                    }
               }
          }
     }

     @Override
     public boolean equals(Object object) {

          if (object == this) {
               return true;
          }

          if (!(object instanceof Board)) {
               return false;
          }

          Board board = (Board) object;

          boolean result = true;
          for (int y=(HEIGHT-1); y>=0; y--) {
               for (int x=0; x<WIDTH; x++) {
                    if ((occupied[x][y] != board.isOccupied(x, y)) ||
                            (occupyingPlayer[x][y] != board.getOccupyingPlayer(x, y))) {
                         result = false;
                         break;
                    }
               }
          }
          return result;
     }

     @Override
     public String toString() {
          StringBuilder boardAsString = new StringBuilder("\n");
          for (int y=(HEIGHT-1); y>=0; y--) {
               for (int x=0; x<WIDTH; x++) {
                    if (!(occupied[x][y])) {
                         boardAsString.append(". ");
                    } else if (occupyingPlayer[x][y] == 0) {
                         boardAsString.append("X ");
                    } else {
                         boardAsString.append("O ");
                    }
               }
               boardAsString.append("\n");
          }
          return super.toString() + boardAsString.toString();
     }
}
