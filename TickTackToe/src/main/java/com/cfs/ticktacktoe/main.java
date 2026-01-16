package com.cfs.ticktacktoe;


import java.util.ArrayList;
import java.util.List;

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

public class main {

}
