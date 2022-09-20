package echo;

import SimpleBot.SimpleBotPlayer;
import model.*;

import java.util.ArrayList;

public class SmarterBotPlayer extends SimpleBotPlayer {
    protected Player opponent;
    protected static Board board;

    public SmarterBotPlayer(int playerNo) {
        super(playerNo);
    }

    @Override
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public Move makeMove(Board board) {

        ArrayList<Move> moves = getPlayerMoves(this, board);
        Board possibleBoard = new Board(board);
        possibleBoard.makeMove(moves.get(0));
        // Todo
        return makeMove(board);
    }

    public Board AI(Move move, Player player, Board board) {
        Gamepiece piece = move.getGamepiece();
        Board current = new Board(board);
        Location[] location = piece.getLocations();
        int x = move.getLocation().getX();
        int y = move.getLocation().getY();
        boolean[][] Occ = current.getOccupied();
        int[][] OccPlayer = current.getOccupyingPlayer();
        for (Location location1 : location) {
            Occ[location1.getX() + x][location1.getY() + y] = true;
            OccPlayer[location1.getX() + x][location1.getY() + y] = player.getPlayerNo();
        }
        current.setOccupied(Occ);
        current.setOccupyingPlayer(OccPlayer);
        return current;
    }

    public int minimax(ArrayList<Move> moves, Board board, int depth, boolean isMaximizing) {
        //if the game is over
        if (!board.playerHasMoves(this) && !board.playerHasMoves(this.opponent)) {
            return this.playerScore();
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        if (isMaximizing) {
            for (Move move : moves) {
                Board board1 = AI(move, opponent, board);
                ArrayList<Move> newMoves = getPlayerMoves(this.opponent, board1);
                min = minimax(newMoves, board1, depth + 1, false);
                max = Math.max(min, max);
            }
            return max;
        }else {
            for (Move move : moves) {
                Board board1 = AI(move, opponent, board);
                ArrayList<Move> newMoves = getPlayerMoves(this.opponent, board1);
                min = minimax(newMoves, board1, depth + 1, true);
                max = Math.min(min, max);
            }
            return max;
        }
    }
}
