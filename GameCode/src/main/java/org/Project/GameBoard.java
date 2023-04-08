package org.Project;

import java.util.ArrayList;
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
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
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
    /**this function verigy taht the tile at coordinates c has at east 1 free sid and can be picked
     this function will be called on maximum 3 tiles and after it returns true for al the tiles they will be picked */
    public boolean verifyPickable(Coordinates c){
        int n = negativeMatrix.length;
        int m = negativeMatrix[0].length;
        int i = c.getX();
        int j = c.getY();

        if ((isValidPos(i - 1, j, n, m) && !board[i-1][j].isOccupied()|| !isValidPos(i -1, j, n, m))) {
            for (i = i-1; i>=0; i--){
                if (board[i][j].isOccupied())
                    return false;

            }
            return true;


        }

        if ((isValidPos(i, j - 1, n, m)&&!board[i][j-1].isOccupied()) || !isValidPos(i, j - 1, n, m)) {
            for (j = j-1; j>=0; j--){
                if (board[i][j].isOccupied())
                    return false;
            }
            return true;
        }

        if ((isValidPos(i, j + 1, n, m)&&!board[i][j+1].isOccupied()) || !isValidPos(i, j + 1, n, m)) {
            for (j = j+1; j<board[0].length; j++){
                if (board[i][j].isOccupied())
                    return false;
            }
            return true;
        }

        if (isValidPos(i + 1, j, n, m)&&!board[i+1][j].isOccupied() || !isValidPos(i + 1, j, n, m)) {
            for (i = i+1; i<board.length; i++){
                if (board[i][j].isOccupied())
                    return false;
            }
            return true;
        }



        return false;

    }

    public Tile pick(Coordinates c) {

        try {
            Tile picked_Tile = board[c.getX()][c.getY()].removeTile();
            ;
            return picked_Tile;

        } catch (IllegalStateException e) {
            throw new IllegalStateException("prova", e);
        }


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
    //print colored game Board
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

