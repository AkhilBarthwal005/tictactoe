package model;

import exceptions.BotNotFoundException;
import exceptions.InvalidValidPlayerCountException;
import exceptions.UniqueSymbolNotFoundException;
import strategy.winningstrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextPlayerMoveIndex;
    private List<WinningStrategy> winningStrategies;


    private Game(int size, List<Player> players , List<WinningStrategy> winningStrategies){
        this.board = new Board(size);
        this.players = players;
        this.moves = new ArrayList<>();
        this.gameState = GameState.PROGRESS;
        this.nextPlayerMoveIndex = 0;
        this.winningStrategies = winningStrategies;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextPlayerMoveIndex() {
        return nextPlayerMoveIndex;
    }

    public void setNextPlayerMoveIndex(int nextPlayerMoveIndex) {
        this.nextPlayerMoveIndex = nextPlayerMoveIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public static GameBuilder getBuilder(){
        return new GameBuilder();
    }

    public void makeMove() {
        Player currentPlayer = players.get(nextPlayerMoveIndex);
        System.out.println("It's " + currentPlayer.getName() +" turns ! Please make a move");

        Move move = currentPlayer.makeMove(board); // players make a move
        System.out.println(currentPlayer.getName() + " has made a move at row: " + move.getCell().getRow() + " column: " + move.getCell().getCol() + ".");
        // check it is valid or not
        if(isValidMove(move)){
            moves.add(move);
        }
        // getting row and col of move played by player
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Cell cell = board.getBoard().get(row).get(col);
        // updating the cell in which player play his/her move
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(currentPlayer);
        // setting the updated cell to move
        move.setCell(cell);
        move.setPlayer(currentPlayer);
        // adding the final updated move to the moves list
        moves.add(move);

        // check that after playing a move we get a winner or not

        if(checkWinner(move)){
            setGameState(GameState.WIN);
            winner = currentPlayer;
        }
        else if(moves.size() == (2*(board.getSize() * board.getSize()))){
            setGameState(GameState.DRAW);
        }
        System.out.println(moves.size());

        // updating the player index.
        nextPlayerMoveIndex =  ++nextPlayerMoveIndex%players.size();
    }

    private boolean isValidMove(Move move) {
        if(move == null) {
            setGameState(GameState.DRAW);
            return  false;
        }
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        return row < board.getSize() && col < board.getSize() && !move.getCell().getCellState().equals(CellState.BLOCKED) && !move.getCell().getCellState().equals(CellState.FILLED);
    }

    public void display() {
        board.display();
    }

    public GameState checkState() {
        return gameState;
    }

    public boolean checkWinner(Move lastMove) {
        for (int i = 0; i < winningStrategies.size(); i++) {
            WinningStrategy winningStrategy = winningStrategies.get(i);
            if(winningStrategy.checkWinner(board , lastMove)) {
                return true;
            }
        }
        return false;
    }

    public void undo(Game game) {
        if(moves.isEmpty()){
            System.out.println("There is no move to undo");
            return;
        }
        Move lastMove = moves.get(moves.size() - 1);

        Cell cell = lastMove.getCell();
        cell.setPlayer(null);
        cell.setCellState(CellState.EMPTY);

        nextPlayerMoveIndex-=1;
        nextPlayerMoveIndex = (nextPlayerMoveIndex + players.size())%players.size();

        for (WinningStrategy winningStrategy:
            winningStrategies) {
            winningStrategy.handleUndo(board,lastMove);
        }
    }

    public Player getCurrentPlayer() {
        int index = nextPlayerMoveIndex;
        index-=1;
        index = (index + players.size())%players.size();
        return players.get(index);
    }


    public static class GameBuilder{
        private int size;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public GameBuilder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public GameBuilder setSize(int size) {
            this.size = size;
            return this;
        }



        public Game build() throws BotNotFoundException, InvalidValidPlayerCountException, UniqueSymbolNotFoundException {

            validateBotCount();
            validateNoOfPlayers();
            validateUniqueSymbols();
            return new Game(size,players,winningStrategies);
        }

        private void validateUniqueSymbols() throws UniqueSymbolNotFoundException {
            Set<Character> collect = players.stream().map(x -> x.getSymbol().getCharacter()).collect(Collectors.toSet());
            if(collect.size() != players.size()){
                throw  new UniqueSymbolNotFoundException("Provide unique symbol to each players");
            }
        }

        private void validateNoOfPlayers() throws InvalidValidPlayerCountException {
            if(size!= players.size()+1){
                throw new InvalidValidPlayerCountException("Players count should be exactly one lesser then the board size");
            }
        }

        private void validateBotCount() throws BotNotFoundException {
            boolean exist = players.stream().anyMatch(x -> x.getPlayerType() == PlayerType.BOT);

            if(!exist){
                throw new BotNotFoundException("Game should consist of at least one bot");
            }

        }


    }
}
