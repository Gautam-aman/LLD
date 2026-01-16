package com.cfs.ticktacktoe;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

interface IObserver {
    void update(String msg);
}

class Symbol {
    private final String symbol;

    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;
        Symbol other = (Symbol) o;
        return symbol != null ? symbol.equals(other.symbol) : other.symbol == null;
    }

    @Override
    public int hashCode() {
        return symbol != null ? symbol.hashCode() : 0;
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

    public boolean placeMark(int row, int col, Symbol symbol) {
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

    public int getSize() {
        return size;
    }

    public Symbol getEmptyCell() {
        return emptyCell;
    }

    public void Display() {
        System.out.println("Board:");
        for (List<Symbol> row : grid) {
            for (Symbol symbol : row) {
                System.out.print(symbol.getSymbol() + " ");
            }
            System.out.println();
        }
    }
}

class GamePlayer {
    private final String name;
    private final String id;
    private int score;
    private final Symbol symbol;

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

interface GameRules {
    boolean isValidMove(int row, int col, Board board);

    boolean checkWinCondition(Symbol symbol, Board board);

    boolean checkDrawCondition(Board board);
}

class StandardRules implements GameRules {

    @Override
    public boolean isValidMove(int row, int col, Board board) {
        return board.isCellEmpty(row, col);
    }

    @Override
    public boolean checkWinCondition(Symbol symbol, Board board) {
        int size = board.getSize();

        for (int i = 0; i < size; i++) {
            boolean win = true;
            for (int j = 0; j < size; j++) {
                if (!board.getCell(i, j).equals(symbol)) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        for (int j = 0; j < size; j++) {
            boolean win = true;
            for (int i = 0; i < size; i++) {
                if (!board.getCell(i, j).equals(symbol)) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        boolean win = true;
        for (int i = 0; i < size; i++) {
            if (!board.getCell(i, i).equals(symbol)) {
                win = false;
                break;
            }
        }
        if (win) return true;

        win = true;
        for (int i = 0; i < size; i++) {
            if (!board.getCell(i, size - 1 - i).equals(symbol)) {
                win = false;
                break;
            }
        }
        return win;
    }

    @Override
    public boolean checkDrawCondition(Board board) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(i, j).equals(board.getEmptyCell())) {
                    return false;
                }
            }
        }
        return true;
    }
}

class TickTackToeGame {
    private final Board board;
    private GameRules rules;
    private boolean gameOver;
    private final List<IObserver> observers;
    private final Deque<GamePlayer> players;

    public TickTackToeGame(int boardSize) {
        board = new Board(boardSize);
        rules = new StandardRules();
        observers = new ArrayList<>();
        players = new ArrayDeque<>();
        gameOver = false;
    }

    public void addPlayers(GamePlayer player) {
        players.add(player);
    }

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String msg) {
        for (var it : observers) {
            it.update(msg);
        }
    }

    public void setRules(GameRules rules) {
        this.rules = rules;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void play() {
        if (players.size() < 2) {
            System.out.println("in sufficient players");
            return;
        }

        notifyObservers("Game Started");
        Scanner input = new Scanner(System.in);

        while (!gameOver) {
            board.Display();

            GamePlayer player = players.poll();
            System.out.println("Player " + player.getName() + " is playing");

            int row = input.nextInt();
            int col = input.nextInt();

            if (rules.isValidMove(row, col, board)) {
                board.placeMark(row, col, player.getSymbol());
                notifyObservers("Player " + player.getName() + " played");

                if (rules.checkWinCondition(player.getSymbol(), board)) {
                    board.Display();
                    notifyObservers("Player " + player.getName() + " wins");
                    player.incrementScore();
                    gameOver = true;
                } else if (rules.checkDrawCondition(board)) {
                    board.Display();
                    System.out.println("its a draw");
                    notifyObservers("Game is drawn");
                    gameOver = true;
                } else {
                    players.add(player);
                }
            } else {
                System.out.println("invalid move");
                players.addFirst(player);
            }
        }
    }
}

enum GameType {
    Standard;
}

class GameFactory {
    public static TickTackToeGame createGame(int boardSize, GameType gameType) {
        TickTackToeGame newgame = new TickTackToeGame(boardSize);
        if (gameType == GameType.Standard) {
            newgame.setRules(new StandardRules());
            return newgame;
        }
        System.out.println("invalid game type");
        return null;
    }
}

class ConsoleNotifier implements IObserver {
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

        GamePlayer player1 = new GamePlayer("Aman", "1", new Symbol("X"));
        GamePlayer player2 = new GamePlayer("Kishan", "2", new Symbol("O"));

        game.addPlayers(player1);
        game.addPlayers(player2);

        game.play();
    }
}
