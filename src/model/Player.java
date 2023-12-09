package model;

import java.util.Scanner;

public class Player {
    private Symbol symbol;
    private int id;
    private String name;
    private PlayerType playerType;


    public Player(Symbol symbol, int id, String name, PlayerType playerType) {
        this.symbol = symbol;
        this.id = id;
        this.name = name;
        this.playerType = playerType;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
    public Move makeMove(Board board) {
        Scanner sc =new Scanner(System.in);
        System.out.println("Please tell the row count where you want to make a move (Starting from 0): ");
        int row = sc.nextInt();

        System.out.println("Please tell the column count where you want to make a move (Starting from 0): ");
        int col = sc.nextInt();

        return new Move(this,new Cell(row,col)); // setting coordinates of the move
    }
}
