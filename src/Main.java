import controller.GameController;
import exceptions.BotNotFoundException;
import exceptions.InvalidValidPlayerCountException;
import exceptions.UniqueSymbolNotFoundException;
import model.*;
import strategy.winningstrategy.ColumnWinningStrategy;
import strategy.winningstrategy.WinningStrategy;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws BotNotFoundException, InvalidValidPlayerCountException, UniqueSymbolNotFoundException {
        int size =3;
        List<Player> players = List.of(new Player(new Symbol('X'),1,"Akhil" , PlayerType.HUMAN),
                new Bot(new Symbol('O'),2,"GPT" , PlayerType.BOT,BotDifficultyLevel.EASY));

        List<WinningStrategy> winningStrategies = List.of(new ColumnWinningStrategy());
        GameController gameController = new GameController();

        Scanner sc = new Scanner(System.in);

        Game game = gameController.createGame(size,players,winningStrategies);

        while(game.getGameState().equals(GameState.PROGRESS)){
            gameController.display(game);
            Player currentPlayer = gameController.getCurrentPlayer(game);
            if(currentPlayer.getPlayerType().equals(PlayerType.HUMAN)){
                System.out.println("Do you want to undo? (y/n)");
                String userInput = sc.nextLine();
                if(userInput.equalsIgnoreCase("y")){
                    gameController.undo(game);
                    continue;
                }
            }
            gameController.makeMove(game);
        }
        gameController.display(game);
        if(gameController.checkState(game).equals(GameState.DRAW)){
            System.out.println("Game has drawn");
        }
        else{
            System.out.println("Game has finished and the winner is: " + gameController.getWinner(game).getName());
        }
    }
}