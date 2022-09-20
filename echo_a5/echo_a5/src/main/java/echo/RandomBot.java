/**
 * echo
 * 20201034
 */
package echo;

import SimpleBot.SimpleBotPlayer;
import model.*;

import java.util.ArrayList;

public class RandomBot extends SimpleBotPlayer { //Bot that makes random moves to help train EchoBot
    int moveNo;

    public RandomBot(int playerNo) {
        super(playerNo);
        this.moveNo = 0;
    }

    @Override
    public Move makeMove(Board board) {
        ArrayList<Move> moves = getPlayerMoves(this, board);
        int index = (int)(Math.random() * moves.size());
        moveNo++;
        return moves.get(index);
    }

    @Override
    public ArrayList<Move> getMovesWithGivenGamepiece(Gamepiece gamepiece, String gamepieceName, Player player, Board board){
        ArrayList<Move> moves = new ArrayList<Move>();
        Gamepiece clonedPiece = new Gamepiece(gamepiece);
        for (int i = 0; i < 4 ; i++) {
            moves.addAll(getMovesWithGivenOrientation(clonedPiece,gamepieceName,player,board));
            clonedPiece.rotateRight();
        }
        clonedPiece.flipAlongY();
        for (int i = 0; i < 4 ; i++) {
            moves.addAll(getMovesWithGivenOrientation(clonedPiece,gamepieceName,player,board));
            clonedPiece.rotateRight();
        }
        return moves;
    }

    @Override
    public ArrayList<Move> getMovesWithGivenOrientation(Gamepiece piece, String gamepieceName, Player player, Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Move move = new Move(player,piece, gamepieceName,new Location(0,0));
        for (int x = 0; x < board.WIDTH; x++) {
            for (int y = 0; y < board.HEIGHT; y++) {
                move.getLocation().setX(x);
                move.getLocation().setY(y);
                if ((moveNo == 0 && board.isValidFirstMove(move)) || (moveNo != 0 && board.isValidSubsequentMove(move))) {
                    moves.add(new Move(move));
                }
            }
        }
        return moves;
    }
}
