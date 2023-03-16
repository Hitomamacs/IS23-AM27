package org.example;

import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    private Spot[][] board;
    private int[][] negativeMatrix;
    private int[][] oneMatrix;
    private int[][] finalMatrix;

    public GameBoard(int rows, int columns, int players){
        TrueM.addMatrix(TrueM.negativeMatrix1);
        //TODO add exception
        //code that searches for the first matrix that has rows == rows and columns == columns from the list NegativeMatrix.negativematrixlist
        for(int i = 0; i< TrueM.negativeMatrixList.size(); i++){
            int[] dim = TrueM.returndim(TrueM.negativeMatrixList.get(i));
            if(dim[0] == rows && dim[1] == columns && TrueM.negativeMatrixList.get(i)[(rows-1)/2][(columns-1)/2] == players){
                negativeMatrix = TrueM.negativeMatrixList.get(i);
                break;}}


        oneMatrix = new int[rows][columns];
        for(int i=0; i<rows; i++) {
            for (int j = 0; j < columns; j++) {
                oneMatrix[i][j] = 1;
            }
        }

        //multiply oneMatrix with negativeMatrix and save result in finalMatrix
        finalMatrix = new int[rows][columns];
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                finalMatrix[i][j] = oneMatrix[i][j] * negativeMatrix[i][j];
            }
        }

        board = new Spot[rows][columns];
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                board[i][j] = new Spot(false, null);
            }


        }


    }

    //code that prints final matrix in console
    public void printM(){
        for(int i = 0; i< returndim()[0]; i++){
            for (int j = 0; j< returndim()[1]; j++){

                System.out.print(finalMatrix[i][j] + " ");
            }
            System.out.println(); //change line on console as row comes to end in the matrix.
        }
    }



    //functions that returns dimension of matrix
    private int[] returndim(){
        int[] dim = new int[2];
        dim[0] = finalMatrix.length;
        dim[1] = finalMatrix[1].length;
        return dim;
    }



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
        int[] dim = returndim();
        int rows = dim[0];
        int columns = dim[1];
        while (tiles.size() > 0){
            for(int i=0; i<rows; i++){
                for(int j=0; j<columns; j++){
                    if(finalMatrix[i][j] != 0){
                        board[i][j].placeTile(tiles.iterator().next());
                        tiles.remove(tiles.iterator().next());
                    }
                }
            }
        }


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

