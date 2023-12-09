package controller;

import exceptions.BotNotFoundException;
import exceptions.InvalidValidPlayerCountException;
import exceptions.UniqueSymbolNotFoundException;
import model.Game;
import model.GameState;
import model.Player;
import strategy.winningstrategy.WinningStrategy;

import java.util.List;

public class GameController {

    public Game createGame(int size , List<Player>  players, List<WinningStrategy> winningStrategies) throws BotNotFoundException, InvalidValidPlayerCountException, UniqueSymbolNotFoundException {
        return Game.getBuilder().setSize(size).setPlayers(players).setWinningStrategies(winningStrategies).build();
    }

    public void makeMove(Game game){
        game.makeMove();
    }

    public void display(Game game){
        game.display();

    }

    public void undo(Game game){
        game.undo(game);
    }

    public GameState checkState(Game game){
        return game.checkState();
    }

    public  Player getWinner(Game game){
        return game.getWinner();
    }

    public Player getCurrentPlayer(Game game){
        return game.getCurrentPlayer();
    }


}
