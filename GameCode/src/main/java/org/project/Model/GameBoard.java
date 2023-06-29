package org.project.Model;

import com.google.gson.annotations.Expose;
import org.project.ClientPack.ObservableObject;
import org.project.Controller.Control.GameOrchestrator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * The class represents the game board
 */
public class GameBoard extends ObservableObject {
    @Expose
    private Spot[][] board;
    @Expose
    private int[][] oneMatrix;
    @Expose
    private int[][] finalMatrix;

    public ArrayList<Tile> getAdjcecent() {
        return adjcecent;
    }

    @Expose
    private ArrayList<Tile> adjcecent = new ArrayList<Tile>();

    /**
     * A different game board is created according to the number of players.
     * The board is a matrix of Spots and at the beginning each Spot has the occupied attribute set to false because there is no tile
     * @param rows This is the number of board's rows
     * @param columns This is the number of board's columns
     * @param players This is the number of players
     */
    public GameBoard(int rows, int columns, int players){
        TrueM.addMatrix(TrueM.negativeMatrix1);
        TrueM.addMatrix(TrueM.negativematrix2);
        TrueM.addMatrix(TrueM.negativematrix3);

        //Code that searches for the first matrix that has rows == rows and columns == columns from the list NegativeMatrix.negativeMatrixList
        if(players == 4)
            finalMatrix = TrueM.negativeMatrix1;
        else if(players == 2)
            finalMatrix = TrueM.negativematrix2;
        else if(players == 3)
            finalMatrix = TrueM.negativematrix3;

        board = new Spot[rows][columns];
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                board[i][j] = new Spot(false, null);
            }
        }
    }

    public GameBoard (GameOrchestrator orchestrator){
        this.board = orchestrator.getGameBoard().getBoard();
        this.oneMatrix = orchestrator.getGameBoard().getOneMatrix();
        this.finalMatrix = orchestrator.getGameBoard().getFinalMatrix();
        this.adjcecent = orchestrator.getGameBoard().getAdjcecent();
    }


    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static boolean isValidPos(int i, int j, int n, int m){

        if (i < 0 || j < 0 || i > n - 1 || j > m - 1) {
            return false;
        }
        return true;
    }

    /**
     * Code that prints the final matrix in console
     */
    public void printM(){
        for(int i = 0; i< returndim()[0]; i++){
            for (int j = 0; j< returndim()[1]; j++){

                System.out.print(finalMatrix[i][j] + " ");
            }
            System.out.println(); //change line on console as row comes to end in the matrix.
        }
    }

    /**
     * The method prints a representation of the board matrix,
     * showing occupied elements as "2" and unoccupied elements with specific values from the finalMatrix array.
     * */
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

    /**
     * Functions that returns the dimension of the matrix
     */
    public int[] returndim(){
        int[] dim = new int[2];
        dim[0] = finalMatrix.length;
        dim[1] = finalMatrix[1].length;
        return dim;
    }

    /**
     * @param i Position i on the board
     * @param j Position j on the board
     * @return Returns an ArrayList of Tile representing the cells adjacent to the element at position [i][j] of the board that are occupied.
     */
    private ArrayList<Tile> get_adjcent(int i, int j){
        int n = finalMatrix.length;
        int m = finalMatrix[0].length;
        ArrayList<Tile> v = new ArrayList<Tile>();

        // Checking for all the possible adjacent positions

        if (isValidPos(i - 1, j, n, m) && board[i-1][j].isOccupied()) {
            v.add(board[i-1][j].getTile());
        }
        if (isValidPos(i, j - 1, n, m)&&board[i][j-1].isOccupied()) {
            v.add(board[i][j-1].getTile());
        }
        if (isValidPos(i, j + 1, n, m)&&board[i][j+1].isOccupied()) {
            v.add(board[i][j+1].getTile());
        }
        if (isValidPos(i + 1, j, n, m)&&board[i+1][j].isOccupied()) {
            v.add(board[i+1][j].getTile());
        }
        // Returning the arraylist
        return v;
    }

    /**
     * The method checks if each occupied element in the board matrix has no adjacent occupied squares.
     * @return true if the check is successful, otherwise false.
     */
    public boolean checkBoard(){
        ArrayList<Tile> adjcecent = new ArrayList<Tile>();
        for(int i = 0; i< board.length; i++ ) {
            for (int j = 0; j< board[0].length; j++){
                adjcecent = get_adjcent(i,j);
                if((!adjcecent.isEmpty()) && board[i][j].isOccupied())
                    for (Tile tile: adjcecent){
                        if (tile != null)
                            return false;
                    }
            }
        }
        return true;
    }

    /**
     * The method counts the number of valid (non-zero) spots in the finalMatrix array.
     * @return the number of valid spots
     */
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

    /**
     * @return The number of valid positions in the finalMatrix that are unoccupied in the board.
     * @throws NotToRefillBoardExc if the check fails
     */
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

    /**
     * This function verifies that the tile at coordinates c has at least one free side so that can be picked.
     * This function will be called on maximum 3 tiles, after it returns true or false for all the tiles that will be picked
     */
    public boolean verifyPickable(Coordinates c){
        int i = c.getX();
        int j = c.getY();
        if (!board[i][j].isOccupied()) {
            return false;
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
        for (int[] dir : directions) {
            if (isUnoccupiedInDirection(i, j, dir[0], dir[1])) {
                return true;
            }
        }
        return false;
    }

    private boolean isUnoccupiedInDirection(int i, int j, int di, int dj) {
        int n = board.length;
        int m = board[0].length;
        i += di;
        j += dj;
        if (!isValidPos(i, j, n, m) || !board[i][j].isOccupied()) {
            return true;
        }
        return false;
    }

    /**
     * @param c coordinates of the board
     * @return The Tile object from the specified position in the board array.
     * If an exception occurs during removal, an exception is thrown with a custom message.
     */
    public Tile pick(Coordinates c) {
        try {
            Tile picked_Tile = board[c.getX()][c.getY()].removeTile();
            return picked_Tile;

        } catch (IllegalStateException e) {
            throw new IllegalStateException("prova", e);
        }
    }

    /**
     * The method fills the board matrix with the Tile objects provided in the set tiles,
     * placing them in the valid and available positions of the matrix.
     * @param tiles Set of Tile
     */
    public void fillBoard(Set<Tile> tiles) {
        int[] dim = returndim();
        int rows = dim[0];
        int columns = dim[1];

        Iterator<Tile> tileIterator = tiles.iterator();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (finalMatrix[i][j] != 0 && !board[i][j].isOccupied() && tileIterator.hasNext()) {
                    Tile tileToPlace = tileIterator.next();
                    board[i][j].placeTile(tileToPlace);
                    tileIterator.remove();
                }
            }
        }
    }

    /**
     * This method prints the coloured board
     */
    public void printBoardColor(){
        int[] dim = returndim();
        int rows = dim[0];
        int columns = dim[1];
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                if(finalMatrix[i][j] == 0){
                    System.out.print("  ");
                }
                else if (board[i][j].isOccupied()){
                    if(board[i][j].getTile().getColor() == Color.PINK)
                        System.out.print(ANSI_PURPLE + "P ");
                    else if(board[i][j].getTile().getColor() == Color.BLUE)
                        System.out.print(ANSI_BLUE + "B " );
                    else if(board[i][j].getTile().getColor() == Color.GREEN)
                        System.out.print(ANSI_GREEN + "G ") ;
                    else if(board[i][j].getTile().getColor() == Color.YELLOW)
                        System.out.print(ANSI_YELLOW + "Y ");
                    else if(board[i][j].getTile().getColor() == Color.AZURE)
                        System.out.print(ANSI_CYAN + "A ");
                    else if(board[i][j].getTile().getColor() == Color.WHITE)
                        System.out.print(ANSI_WHITE + "W ");
                }
                else
                    System.out.print(ANSI_BLACK+"0 ");
            }
            System.out.println();
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
    public int[][] getOneMatrix() {
        return oneMatrix;
    }
    public void setOneMatrix(int[][] oneMatrix) {
        this.oneMatrix = oneMatrix;
    }
}
