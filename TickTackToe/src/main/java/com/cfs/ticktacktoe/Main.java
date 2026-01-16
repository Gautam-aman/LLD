package com.cfs.ticktacktoe;


import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

// Board with SRP
interface IObserver{
    public void update(String msg);
}

class Symbol{
    private String symbol;
    public Symbol(String symbol) {
        this.symbol = symbol;
    }
    public String getSymbol() {
        return symbol;
    }
}

class Board {
    private final List<List<Symbol>> grid;
    private final int size;
    private final Symbol emptyCell;

    public Board(int size) {
        this.size = size;
        this.emptyCell = new Symbol("-");

        grid = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            List<Symbol> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                row.add(emptyCell);
            }
            grid.add(row);
        }
    }

    public boolean isCellEmpty(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            return false;
        }
        return grid.get(row).get(col).equals(emptyCell);
    }

    public Boolean placeMark(int row , int col , Symbol symbol) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            return false;
        }
        if (!isCellEmpty(row, col)) {
            return false;
        }
        grid.get(row).set(col, symbol);
        return true;
    }

    public Symbol getCell(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            return null;
        }
        return grid.get(row).get(col);
    }

    public int getSize(){
        return size;
    }

    public Symbol getEmptyCell() {
        return emptyCell;
    }

    public void Display(){
        System.out.println("Board:");
        for (List<Symbol> row : grid) {
            for (Symbol symbol : row) {
                System.out.println(symbol.getSymbol());
            }
            System.out.printf("\n");
        }
    }
}

class GamePlayer{
    private String name;
    private String id;
    private int score;
    private Symbol symbol;

    public GamePlayer(String name, String id, Symbol symbol) {
        this.name = name;
        this.id = id;
        this.score = 0;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }
    public Symbol getSymbol() {
        return symbol;
    }
}

interface GameRules{
    public boolean isValidMove(int row, int col , Board board);
    public boolean checkWinCondition(Symbol symbol, Board board);
    public boolean checkDrawCondition(Board board);
}

 class StandardRules implements GameRules{

     @Override
     public boolean isValidMove(int row, int col, Board board) {
         return board.isCellEmpty(row, col);
     }

     @Override
     public boolean checkWinCondition(Symbol symbol, Board board) {
         int size = board.getSize();
         for(int i = 0 ; i < size ; i++){
             boolean win = false;
             for(int j = 0 ; j < size ; j++){
                 if (!board.getCell(i, j).equals(symbol)){
                     win = false;
                     break;
                 }
             }
             if(win){
                 return true;
             }
         }

         // check col
         for(int i = 0 ; i < size ; i++){
             boolean win = false;
             for(int j = 0 ; j < size ; j++){
                 if(!board.getCell(i, j).equals(symbol)){
                     win = false;
                 }
             }
             if(win){
                 return true;
             }
         }

         // check diag
         boolean win = true;
         for (int i = 0 ; i < size ; i++){
             if (!board.getCell(i, i).equals(symbol)){
                 win = false;
                 break;
             }
         }
         if(win){
             return true;
         }

         // anti diag
         for(int i = 0 ; i < size ; i++){
             if (!board.getCell(i, size-1-i).equals(symbol)){
                 win = false;
             }
         }
         if(win){
             return true;
         }

         return false;

     }

     @Override
     public boolean checkDrawCondition(Board board) {
         for(int i = 0; i < board.getSize(); i++){
             for(int j = 0; j < board.getSize(); j++){
                 if (board.getCell(i,j) == board.getEmptyCell()){
                     return true;
                 }
             }
         }
         return false;
     }
 }

 class TickTackToeGame{
    private Board board;
    private GameRules rules;
    private boolean gameOver;
    List<IObserver> observers;
    private Deque<GamePlayer> players;

    public TickTackToeGame(int boardSize) {
        board = new Board(boardSize);
        rules = new StandardRules();
        observers = new ArrayList<>();
    }

    public void addPlayers(GamePlayer player){
        players.add(player);
    }

    public void addObserver(IObserver observer){
        observers.add(observer);
    }

    public void notifyObservers(String msg){
        for(var it : observers){
            it.update(msg);
        }
    }

    public void setRules(GameRules rules){
        this.rules = rules;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void play(){
        if (players.size() < 2){
            System.out.println("in sufficient players");
        }

    }

 }

 enum GameType{
    Standard; // Loose coupling
 }

 class GameFactory{
    public static TickTackToeGame createGame(int boardSize, GameType gameType){
        TickTackToeGame newgame = new TickTackToeGame(boardSize);
        if (gameType == GameType.Standard){
            newgame.setRules(new StandardRules());
            return newgame;
        }
        System.out.println("invalid game type");
        return null;
    }
 }

 class ConsoleNotifier implements IObserver{

     @Override
     public void update(String msg) {
         System.out.println(msg);
     }
 }

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int boardSize;
        System.out.println("Enter the size of the board");
        boardSize = sc.nextInt();
        TickTackToeGame game = GameFactory.createGame(boardSize, GameType.Standard);
        IObserver observer = new ConsoleNotifier();
        game.addObserver(observer);
        GamePlayer player1 = new GamePlayer("Aman" , "1" , new Symbol("X"));
        GamePlayer player2 = new GamePlayer("Kishan" , "2" , new Symbol("O"));
        game.addPlayers(player1);
        game.addPlayers(player2);
        game.play();
    }
}
