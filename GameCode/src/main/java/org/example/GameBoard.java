package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameBoard {
    private Spot[][] board;
    private int[][] negativeMatrix;
    private int[][] oneMatrix;
    private int[][] finalMatrix;
    private ArrayList<Tile> adjcecent = new ArrayList<Tile>();

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
    static boolean isValidPos(int i, int j, int n, int m)
    {
        if (i < 0 || j < 0 || i > n - 1 || j > m - 1) {
            return false;
        }
        return true;
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
    /** functions that prints the board and the tile rappresented by the number 2 == tiles 1 == valid Spot */
    public boolean printMwithTiles() {
        for(int i = 0; i< returndim()[0]; i++){
            for (int j = 0; j< returndim()[1]; j++){
                if (board[i][j].isOccupied()){
                    System.out.print(2 + " ");
                }
                else if (!board[i][j].isOccupied()){
                    System.out.print(finalMatrix[i][j] + " ");
                }


            }
            System.out.println(); //change line on console as row comes to end in the matrix.
        }
        System.out.println();
        return true;

    }



    //functions that returns dimension of matrix
    public int[] returndim(){
        int[] dim = new int[2];
        dim[0] = finalMatrix.length;
        dim[1] = finalMatrix[1].length;
        return dim;
    }

    private ArrayList<Tile> get_adjcent(int i, int j){
        int n = negativeMatrix.length;
        int m = negativeMatrix[0].length;
        ArrayList<Tile> v = new ArrayList<Tile>();


        // Checking for all the possible adjacent positions
        if (isValidPos(i - 1, j - 1, n, m) && board[i-1][j-1].isOccupied()) {
            v.add(board[i-1][j-1].getTile());
        }
        if (isValidPos(i - 1, j, n, m) && board[i-1][j].isOccupied()) {
            v.add(board[i-1][j].getTile());
        }
        if (isValidPos(i - 1, j + 1, n, m) && board[i-1][j+1].isOccupied()) {
            v.add(board[i-1][j+1].getTile());
        }
        if (isValidPos(i, j - 1, n, m)&&board[i][j-1].isOccupied()) {
            v.add(board[i][j-1].getTile());
        }
        if (isValidPos(i, j + 1, n, m)&&board[i][j+1].isOccupied()) {
            v.add(board[i][j+1].getTile());
        }
        if (isValidPos(i + 1, j - 1, n, m)&& board[i+1][j-1].isOccupied()) {
            v.add(board[i+1][j-1].getTile());
        }
        if (isValidPos(i + 1, j, n, m)&&board[i+1][j].isOccupied()) {
            v.add(board[i+1][j].getTile());
        }
        if (isValidPos(i + 1, j + 1, n, m)&&board[i+1][j+1].isOccupied()) {
            v.add(board[i+1][j+1].getTile());
        }

        // Returning the arraylist
        return v;
    }

    public boolean checkBoard(){
        ArrayList<Tile> adjcecent = new ArrayList<Tile>();
        for(int i = 0; i< board.length; i++ ) {
            for (int j = 0; j< board[0].length; j++){
                adjcecent = get_adjcent(i,j);
                if((!adjcecent.isEmpty()) && board[i][j].isOccupied()  )
                    for (Tile tile: adjcecent){
                        if (tile != null)
                            return false;
                    }
            }
        }
        return true;
    }

    private int getNumbervalidSpot(){
        int num = 0;
        for(int i = 0; i< board.length; i++ ) {
            for (int j = 0; j< board[0].length; j++){
                if (finalMatrix[i][j] != 0)
                    num++;
            }
        }
        return num;
    }

    public int boardCheckNum() throws NotToRefillBoardExc {
        if (!checkBoard())
            throw new NotToRefillBoardExc("Board not to refill");
        else {
            int num = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j].isOccupied())
                        num++;
                }
            }
            return getNumbervalidSpot() - num;
        }

    }


    public Set<Tile> pick(Coordinates c) {
        Set<Tile> return_tiles = new HashSet<Tile>();
        try {
            Tile picked_Tile = board[c.getX()][c.getY()].removeTile();
            return_tiles.add(picked_Tile);
            return return_tiles;

        } catch (IllegalStateException e) {
            throw new IllegalStateException("prova", e);
        }


    }

    public Set<Tile> pick(Coordinates c1, Coordinates c2){
        Set<Tile> return_tiles = new HashSet<Tile>();
        try{
            Tile picked_Tile1 = board[c1.getX()][c1.getY()].removeTile();
            Tile picked_Tile2 = board[c2.getX()][c2.getY()].removeTile();
            return_tiles.add(picked_Tile1);
            return_tiles.add(picked_Tile2);
        }catch (IllegalStateException e) {
            System.out.print("Tile not present");
            throw new IllegalStateException();


    }
        return return_tiles;
    }

    public Set<Tile> pick(Coordinates c1, Coordinates c2, Coordinates c3){
            Set<Tile> return_tiles = new HashSet<Tile>();
            try {
                Tile picked_Tile1 = board[c1.getX()][c1.getY()].removeTile();
                Tile picked_Tile2 = board[c2.getX()][c2.getY()].removeTile();
                Tile picked_Tile3 = board[c3.getX()][c3.getY()].removeTile();
                return_tiles.add(picked_Tile1);
                return_tiles.add(picked_Tile2);
                return_tiles.add(picked_Tile3);

            }catch (IllegalStateException e) {
                System.out.print("Tile not present");
                throw new IllegalStateException();



            }

        return return_tiles;
    }

    public void fillBoard(Set<Tile> tiles){
        int[] dim = returndim();
        int rows = dim[0];
        int columns = dim[1];
            for(int i=0; i<rows; i++){
                for(int j=0; j<columns; j++){
                    if(finalMatrix[i][j] != 0 && !board[i][j].isOccupied())
                        while (!tiles.isEmpty()) {
                            board[i][j].placeTile(tiles.iterator().next());
                            tiles.remove(tiles.iterator().next());
                            break;
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

