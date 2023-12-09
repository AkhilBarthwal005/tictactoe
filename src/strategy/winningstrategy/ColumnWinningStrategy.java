package strategy.winningstrategy;

import model.Board;
import model.Move;
import model.Player;
import model.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColumnWinningStrategy implements WinningStrategy{

    Map<Integer , Map<Symbol,Integer>> map = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move lastMove) {
        int col = lastMove.getCell().getCol();

        Symbol symbol = lastMove.getPlayer().getSymbol();

        if(!map.containsKey(col)){
            map.put(col,new HashMap<>());
        }

        Map<Symbol, Integer> symbolIntegerMap = map.get(col);
        symbolIntegerMap.put(symbol, symbolIntegerMap.getOrDefault(symbol,0) + 1);
        return symbolIntegerMap.get(symbol) == board.getSize();

    }

    @Override
    public void handleUndo(Board board, Move lastMove) {
        int col = lastMove.getCell().getCol();

        Symbol symbol = lastMove.getPlayer().getSymbol();

        Map<Symbol, Integer> symbolIntegerMap = map.get(col);
        symbolIntegerMap.put(symbol,symbolIntegerMap.get(symbol)-1);
    }
}
