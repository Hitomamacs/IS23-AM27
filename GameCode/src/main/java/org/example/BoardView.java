package org.example;

import java.util.List;
import java.util.Objects;

public class BoardView {
    String[][] board;
    public BoardView(){
        board = new String[9][9];
    }
    public void init(int numPlayers){
        int[][] matrix2P = {{0,0,0,0,0,0,0,0,0},
                            {0,0,0,1,1,0,0,0,0},
                            {0,0,0,1,1,1,0,0,0},
                            {0,0,1,1,1,1,1,1,0},
                            {0,1,1,1,1,1,1,1,0},
                            {0,1,1,1,1,1,1,0,0},
                            {0,0,0,1,1,1,0,0,0},
                            {0,0,0,0,1,1,0,0,0},
                            {0,0,0,0,0,0,0,0,0}};

        int[][] matrix3P = {{0,0,0,1,0,0,0,0,0},
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
    public void updateBoardView(List<Coordinates> coordinates){
        for (Coordinates coordinate : coordinates) {
            board[coordinate.getX()][coordinate.getY()] = "N";
        }
    }
    public void updateBoardView(String[][] newBoardView){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(!Objects.equals(board[i][j], "I")) {
                    board[i][j] = newBoardView[i][j];
                }
            }
        }
    }
    public String[][] getBoardView(){
        return board;
    }
}
