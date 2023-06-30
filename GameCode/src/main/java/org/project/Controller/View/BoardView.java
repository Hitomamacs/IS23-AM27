package org.project.Controller.View;

import org.project.Model.Coordinates;

import java.util.List;
import java.util.Objects;

/**
 * The class represents the view of the game board
 */
public class BoardView {
    private String[][] board;
    public BoardView(){
        board = new String[9][9];
    }

    /**
     * The method selects a predefined board configuration (defined by the matrix2P, matrix3P and matrix4P matrices)
     * based on the number of players
     * @param numPlayers number of the players that are playing the game
     */
    public void init(int numPlayers){
        int[][] matrix2P =
                {{0,0,0,0,0,0,0,0,0},
                {0,0,0,1,1,0,0,0,0},
                {0,0,0,1,1,1,0,0,0},
                {0,0,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,0,0},
                {0,0,0,1,1,1,0,0,0},
                {0,0,0,0,1,1,0,0,0},
                {0,0,0,0,0,0,0,0,0}};

        int[][] matrix3P =
                {{0,0,0,1,0,0,0,0,0},
                {0,0,0,1,1,0,0,0,0},
                {0,0,1,1,1,1,1,0,0},
                {0,0,1,1,1,1,1,1,1},
                {0,1,1,1,1,1,1,1,0},
                {1,1,1,1,1,1,1,0,0},
                {0,0,1,1,1,1,1,0,0},
                {0,0,0,0,1,1,0,0,0},
                {0,0,0,0,0,1,0,0,0}};

        int[][] matrix4P = {{0,0,0,1,1,0,0,0,0},
                {0,0,0,1,1,1,0,0,0},
                {0,0,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,0},
                {0,0,1,1,1,1,1,0,0},
                {0,0,0,1,1,1,0,0,0},
                {0,0,0,0,1,1,0,0,0}};

        if(numPlayers == 2){
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    if(matrix2P[i][j] == 1)
                        board[i][j] = "N";
                    else
                        board[i][j] = "I";
                }
            }
        }
        if(numPlayers == 3){
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    if(matrix3P[i][j] == 1)
                        board[i][j] = "N";
                    else
                        board[i][j] = "I";
                }
            }
        }
        if(numPlayers == 4){
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    if(matrix4P[i][j] == 1)
                        board[i][j] = "N";
                    else
                        board[i][j] = "I";
                }
            }
        }
    }

    /**
     * The method updates the board view with a new configuration represented by the newBoardView
     * @param newBoardView new configuration of the board
     */
    public void updateBoardView(String[][] newBoardView){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(!Objects.equals(board[i][j], "I")) {
                    board[i][j] = newBoardView[i][j];
                }
            }
        }
    }

    public String[][] getBoard(){
        return board;
    }
}