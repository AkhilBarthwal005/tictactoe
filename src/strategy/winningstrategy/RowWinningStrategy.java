package strategy.winningstrategy;

import model.Board;
import model.Move;

public class RowWinningStrategy implements WinningStrategy{
    @Override
    public boolean checkWinner(Board board, Move lastMove) {
        return false;
    }

    @Override
    public void handleUndo(Board board, Move lastMove) {

    }
}
