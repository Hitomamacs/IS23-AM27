package org.example;

import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    private Spot[][] board;
    private int[][] negativeMatrix;
    private int[][] oneMatrix;
    private int[][] finalMatrix;

    public Set<Tile> pick(Coordinates c){
        Tile picked_Tile = board[c.getX()][c.getY()].removeTile();
        Set<Tile> return_tiles = new HashSet<Tile>();
        return_tiles.add(picked_Tile);
        return return_tiles;
    }

    public Set<Tile> pick(Coordinates c1, Coordinates c2){
        Tile picked_Tile1 = board[c1.getX()][c1.getY()].removeTile();
        Tile picked_Tile2 = board[c2.getX()][c2.getY()].removeTile();
        Set<Tile> return_tiles = new HashSet<Tile>();
        return_tiles.add(picked_Tile1);
        return_tiles.add(picked_Tile2);
        return return_tiles;
    }

    public Set<Tile> pick(Coordinates c1, Coordinates c2, Coordinates c3){
        Tile picked_Tile1 = board[c1.getX()][c1.getY()].removeTile();
        Tile picked_Tile2 = board[c2.getX()][c2.getY()].removeTile();
        Tile picked_Tile3 = board[c3.getX()][c3.getY()].removeTile();
        Set<Tile> return_tiles = new HashSet<Tile>();
        return_tiles.add(picked_Tile1);
        return_tiles.add(picked_Tile2);
        return_tiles.add(picked_Tile3);
        return return_tiles;
    }

    public void fillBoard(Set<Tile> tiles){

    }

    public int[][] getFinalMatrix() {
        return finalMatrix;
    }

    public void setFinalMatrix(int[][] finalMatrix) {
        this.finalMatrix = finalMatrix;
    }

    public Spot[][] getBoard() {
        return board;
    }

    public void setBoard(Spot[][] board) {
        this.board = board;
    }

    public int[][] getNegativeMatrix() {
        return negativeMatrix;
    }

    public void setNegativeMatrix(int[][] negativeMatrix) {
        this.negativeMatrix = negativeMatrix;
    }

    public int[][] getOneMatrix() {
        return oneMatrix;
    }

    public void setOneMatrix(int[][] oneMatrix) {
        this.oneMatrix = oneMatrix;
    }
}

