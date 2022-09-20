import model.Board;
import model.Move;
import model.Player;
import ui.UI;

public class TestPlayer extends Player {

        public TestPlayer(int playerNo) {
            super(playerNo);
        }

        @Override
        public Move makeMove(Board board) {
            return null;
        }

        @Override
        public void setUI(UI ui) {
            return;
        }
}
