package strategy.winningstrategy;

import model.Board;
import model.Move;

public interface WinningStrategy {
    boolean checkWinner(Board board, Move lastMove);

    void handleUndo(Board board, Move lastMove);
}
