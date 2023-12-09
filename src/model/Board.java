package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;

    private List<List<Cell>> board;


    public Board(int size) {
        this.size = size;
        this.board = initBoard(size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }


    private List<List<Cell>> initBoard(int size){
        this.board = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            ArrayList<Cell> list = new ArrayList<>();
            for (int col = 0; col < size; col++) {
                list.add(new Cell(row,col));
            }
            board.add(list);
        }
        return board;
    }

    public void display() {
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.size(); col++) {
                Cell cell = board.get(row).get(col);
                if(cell.getCellState().equals(CellState.EMPTY)){
                    System.out.print(" ");
                }
                else{
                    System.out.print(cell.getPlayer().getSymbol().getCharacter());
                }
                if(col< board.size()-1){
                    System.out.print("  |  ");
                }
            }
            if(row<board.size()-1){
                System.out.println();
                for (int col = 0; col < board.size(); col++) {
                    System.out.print("=====");
                }
            }
            System.out.println();

        }
    }
}
