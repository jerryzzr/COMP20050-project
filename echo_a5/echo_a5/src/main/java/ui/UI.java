package ui;

import model.Move;
import model.Player;

public interface UI {
    void updateDisplay();

    String getPlayerName(Player player);

    void noifyBadMove(Move move);

    void displayGameOverMessage();

    void announcePlayerMakingFirstMove(Player player);
}
