package strategy.botplayingstrategy;

import model.*;

public class EasyPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Move makeMove(Board board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if(board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY)){
                    return new Move(null,new Cell(row,col));
                }
            }
        }
        return new Move(null, null);
    }
}
