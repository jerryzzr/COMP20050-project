import model.Board;
import model.Location;
import model.Move;
import model.Player;
import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {

    @Test
    void defaultContrustor_shouldProduceEmptyBoard() {

        out.println("Board test: default constructor should produce empty board");

        Board board = new Board();
        out.printf("\tTesting newly created board for emptiness ...\n");
        for (int x = 0; x < Board.WIDTH; x++) {
            out.printf("\t");
            for (int y = 0; y < Board.HEIGHT; y++) {
                assertEquals (false,board.isOccupied(x, y));
                out.print(". ");
            }
            out.printf("\n");
        }
        out.printf("\tTesting newly created board for number of players = %d \n", board.getNumberOfPlayers());
        assertEquals (2,board.getNumberOfPlayers());
    }

    @Test
    void equals_returnsCorrectResult_whenComparingTwoBoards() {

        out.println("Board test: checking that equals works correctly");

        class Helper {
            void runTest(Board a, Object b, boolean expectedEqual) {
                out.printf("\tTesting board %s against %s, expected equality = %b\n", a, b, expectedEqual);
                assertEquals(expectedEqual,a.equals(b));
            }
        }

        String config =
                ". . . . . . . . . . . . . .\n" +
                ". . . . . . . . . . . . . .\n" +
                ". . . . . . . . . . . . . .\n" +
                ". . . . . X . . . . . . . .\n" +
                ". . . . . X X . . . . . . .\n" +
                ". . . . . . . . . . . . . .\n" +
                ". . . . . . . . . . . . . .\n" +
                ". . . . . . . . . . O . . .\n" +
                ". . . . . . . . . . O . . .\n" +
                ". . . . . . . . . . O . . .\n" +
                ". . . . . . . . . . . . . .\n" +
                ". . . . . . . . . . . . . .\n" +
                ". . . . . . . . . . . . . .\n" +
                ". . . . . . . . . . . . . .\n";

        Board emptyBoard = new Board();
        Board anotherEmptyBoard = new Board();
        Board nonEmptyBoard = new Board(config);

        new Helper().runTest(emptyBoard, emptyBoard, true);
        new Helper().runTest(emptyBoard, anotherEmptyBoard, true);
        new Helper().runTest(emptyBoard, nonEmptyBoard, false);
        new Helper().runTest(emptyBoard, "a string", false);
    }

    @Test
    void makeMove_shouldUpdateBoardCorrectly_whenFirstMoveIsGiven() {

        out.println("Board test: checking that Board correctly reacts to valid and invalid moves");

        class Helper {
            void runTest(Board board, Move givenMove, boolean expectedResult, Board expectedBoard) {
                out.printf("\tTesting move %s against board: %s", givenMove, board);
                boolean returnedResult = board.isValidFirstMove(givenMove);
                if (returnedResult) {
                    board.makeMove(givenMove);
                }
                out.printf(", returnedResult = %b, resultantBoard = %s", returnedResult, board);
                assertEquals(expectedResult, returnedResult);
                assertEquals(expectedBoard, board);
            }
        }

        // Placing initial piece of player 0 (X) on an empty board
        {
            String givenBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board board = new Board(givenBoardConfig);
            Player player = new TestPlayer(0);
            Move givenMove = new Move(player, player.getGamepieceSet().get("V3"), "V3", new Location(4, 9));
            String expectedBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . X . . . . . . . . .\n" +
                            ". . . . X X . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board expectedBoard = new Board(expectedBoardConfig);
            new Helper().runTest(board, givenMove, true, expectedBoard);
        }

        // attempting to place piece for player 0 (X) outside the board
        {
            String givenBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board board = new Board(givenBoardConfig);
            Player player = new TestPlayer(0);
            Move givenMove = new Move(player, player.getGamepieceSet().get("I3"), "I3", new Location(-1, 4));
            String expectedBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board expectedBoard = new Board(expectedBoardConfig);
            new Helper().runTest(board, givenMove, false, expectedBoard);
        }

        // Placing initial piece of player 1 (O) on an empty board
        {
            String givenBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board board = new Board(givenBoardConfig);
            Player player = new TestPlayer(1);
            Move givenMove = new Move(player, player.getGamepieceSet().get("I2"), "I2", new Location(9, 4));
            String expectedBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . O . . . .\n" +
                            ". . . . . . . . . O . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board expectedBoard = new Board(expectedBoardConfig);
            new Helper().runTest(board, givenMove, true, expectedBoard);
        }

        // attempting to place piece for player 1 (O) not covering starting position
        {
            String givenBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board board = new Board(givenBoardConfig);
            Player player = new TestPlayer(1);
            Move givenMove = new Move(player, player.getGamepieceSet().get("I1"), "I1", new Location(3, 9));
            String expectedBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board expectedBoard = new Board(expectedBoardConfig);
            new Helper().runTest(board, givenMove, false, expectedBoard);
        }
    }

    @Test
    void makeMove_shouldUpdateBoardCorrectly_whenSecondAndSubsequentMoveIsGiven() {

            out.println("Board test: checking that Board correctly reacts to valid and invalid moves");

            class Helper {
                void runTest(Board board, Move givenMove, boolean expectedResult, Board expectedBoard) {
                    out.printf("\tTesting move %s against board: %s", givenMove, board);
                    boolean returnedResult = board.isValidSubsequentMove(givenMove);
                    if (returnedResult) {
                        board.makeMove(givenMove);
                    }
                    out.printf(", returnedResult = %b, resultantBoard = %s", returnedResult, board );
                    assertEquals(expectedResult,returnedResult);
                    assertEquals(expectedBoard,board);
                }
            }

            // Placing piece of player 0 (X) touching first piece at a corner
            {
                String givenBoardConfig =
                        ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . X . . . . . . . . .\n" +
                                ". . . . X X . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n";
                Board board = new Board(givenBoardConfig);
                Player player = new TestPlayer(0);
                Move givenMove = new Move(player, player.getGamepieceSet().get("N"), "N", new Location(6, 8));
                String expectedBoardConfig =
                        ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . X . . . . . . . . .\n" +
                                ". . . . X X . . . . . . . .\n" +
                                ". . . . . . X X X . . . . .\n" +
                                ". . . . . X X . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n";
                Board expectedBoard = new Board(expectedBoardConfig);
                new Helper().runTest(board, givenMove, true, expectedBoard);
            }

            // attempting to place a piece for player 0 (X) outside the board
            {
                String givenBoardConfig =
                        ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n";
                Board board = new Board(givenBoardConfig);
                Player player = new TestPlayer(0);
                Move givenMove = new Move(player, player.getGamepieceSet().get("L4"), "L4", new Location(-1, 4));
                String expectedBoardConfig =
                        ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n";
                Board expectedBoard = new Board(expectedBoardConfig);
                new Helper().runTest(board, givenMove, false, expectedBoard);
            }

            // Placing piece of player 1 (O) on an empty board
            {
                String givenBoardConfig =
                        ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . O . . . .\n" +
                                ". . . . . . . . . O . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n";
                Board board = new Board(givenBoardConfig);
                Player player = new TestPlayer(1);
                Move givenMove = new Move(player, player.getGamepieceSet().get("W"), "W", new Location(10, 2));
                String expectedBoardConfig =
                        ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . O . . . .\n" +
                                ". . . . . . . . . O . . . .\n" +
                                ". . . . . . . . . . O O . .\n" +
                                ". . . . . . . . . O O . . .\n" +
                                ". . . . . . . . . O . . . .\n" +
                                ". . . . . . . . . . . . . .\n";
                Board expectedBoard = new Board(expectedBoardConfig);
                new Helper().runTest(board, givenMove, true, expectedBoard);
            }

            // attempting to place piece for player 1 (O) overlapping player 0 (X) piece
            {
                String givenBoardConfig =
                        ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . X X . . . . . . . .\n" +
                                ". . . . X . . . . . . . . .\n" +
                                ". . . . X . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n";
                Board board = new Board(givenBoardConfig);
                Player player = new TestPlayer(1);
                Move givenMove = new Move(player, player.getGamepieceSet().get("V3"), "V3", new Location(4, 10));
                String expectedBoardConfig =
                        ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . X X . . . . . . . .\n" +
                                ". . . . X . . . . . . . . .\n" +
                                ". . . . X . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n" +
                                ". . . . . . . . . . . . . .\n";
                Board expectedBoard = new Board(expectedBoardConfig);
                new Helper().runTest(board, givenMove, false, expectedBoard);
            }
        // attempting to place piece for player 1 (O) touching pre-existing player 1 (O) pieces at a side
        {
            String givenBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . X X . . . . . . . .\n" +
                            ". . . . X . . . . . . . . .\n" +
                            ". . . . X . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . O . . . .\n" +
                            ". . . . . . . . . O . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board board = new Board(givenBoardConfig);
            Player player = new TestPlayer(1);
            Move givenMove = new Move(player, player.getGamepieceSet().get("V3"), "V3", new Location(9, 5));
            String expectedBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . X X . . . . . . . .\n" +
                            ". . . . X . . . . . . . . .\n" +
                            ". . . . X . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . O . . . .\n" +
                            ". . . . . . . . . O . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board expectedBoard = new Board(expectedBoardConfig);
            new Helper().runTest(board, givenMove, false, expectedBoard);
        }
    }

    @Test
    void playerHasMoves_shouldReturnCorrectValues_whenGivenMidGameAndEndgameBoards() {

        out.println("Board test: checking that playerHasMoves() correctly analyses given board configurations");

        class Helper {
            void runTest(Board givenBoard, Player givenPlayer, boolean expectedResult) {
                out.printf("\tChecking whether player %d has moves on board %s:", givenPlayer.getPlayerNo(), givenBoard);
                boolean returnedResult = givenBoard.playerHasMoves(givenPlayer);
                out.printf(" playerHasMoves() = %b\n", returnedResult );
                assertEquals(expectedResult,returnedResult);
            }
        }

        // both players have moves on the following board
        {
            String givenBoardConfig =
                    ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . X X . . . . . . . .\n" +
                            ". . . . X . . . . . . . . .\n" +
                            ". . . . X . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . O . . . .\n" +
                            ". . . . . . . . . O . . . .\n" +
                            ". . . . . . . . . O . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n" +
                            ". . . . . . . . . . . . . .\n";
            Board board = new Board(givenBoardConfig);
            Player player0 = new TestPlayer(0);
            Player player1 = new TestPlayer(1);
            new Helper().runTest(board, player0, true);
            new Helper().runTest(board, player1, true);
        }

        // player1 has moves, but player0 has no moves on the following board without gamepiece I1
        {
            String givenBoardConfig = "X X O O O O X O . . O O . O \n"+
            "X X O X X X X O X O O X O O \n"+
            "O O X . O O O X X X O X O . \n"+
            "O X X X O O X O O O X O X O \n"+
            "O O X O X X X O X X X O X O \n"+
            "X . O O . . X O X O . O X O \n"+
            "X O O . X X O . O O . O X . \n"+
            "X . X X X O O O . O . O X . \n"+
            "X O O O O X O . O X X X O O \n"+
            "O X O X X X . X O O O X O O \n"+
            "O X X O O O X X . . O X . . \n"+
            "O . X X . O O X X O X O X X \n"+
            "O X O O O X X . . O . O O X \n"+
            "X X X X O X X X O O O . X X \n";
            Board board = new Board(givenBoardConfig);

            Player player0 = new TestPlayer(0);
            player0.getGamepieceSet().remove("I1");

            Player player1 = new TestPlayer(1);
            new Helper().runTest(board, player0, false);
            new Helper().runTest(board, player1, true);
        }
        // none of the players have moves on the following board (assuming Players 0 and 1 do not have gamepieces I1)
        {
            String givenBoardConfig = "X X O O O O X O . . O O . O \n"+
                    "X X O X X X X O X O O X O O \n"+
                    "O O X . O O O X X X O X O . \n"+
                    "O X X X O O X O O O X O X O \n"+
                    "O O X O X X X O X X X O X O \n"+
                    "X . O O . . X O X O . O X O \n"+
                    "X O O . X X O . O O . O X . \n"+
                    "X . X X X O O O . O . O X . \n"+
                    "X O O O O X O . O X X X O O \n"+
                    "O X O X X X . X O O O X O O \n"+
                    "O X X O O O X X . . O X . . \n"+
                    "O . X X . O O X X O X O X X \n"+
                    "O X O O O X X O . O . O O X \n"+
                    "X X X X O X X X O O O . X X \n";
            Board board = new Board(givenBoardConfig);
            Player player0 = new TestPlayer(0);
            player0.getGamepieceSet().remove("I1");
            Player player1 = new TestPlayer(1);
            player1.getGamepieceSet().remove("I1");
            new Helper().runTest(board, player0, false);
            new Helper().runTest(board, player1, false);
        }

    }

}
