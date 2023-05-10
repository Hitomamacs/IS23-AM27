package org.project.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrueM {


    static final int[][] negativeMatrix1= {
            {0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 0, 0},

    };

    static final int[][] negativematrix2 = {{0,0,0,0,0,0,0,0,0},
            {0,0,0,1,1,0,0,0,0},
            {0,0,0,1,1,1,0,0,0},
            {0,0,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,0,0},
            {0,0,0,1,1,1,0,0,0},
            {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,0,0,0,0,0}};

    static final int [][] negativematrix3 =  {{0,0,0,1,0,0,0,0,0},
            {0,0,0,1,1,0,0,0,0},
            {0,0,1,1,1,1,1,0,0},
            {0,0,1,1,1,1,1,1,1},
            {0,1,1,1,1,1,1,1,0},
            {1,1,1,1,1,1,1,0,0},
            {0,0,1,1,1,1,1,0,0},
            {0,0,0,0,1,1,0,0,0},
            {0,0,0,0,0,1,0,0,0}};
    static final List<int[][]> negativeMatrixList = new ArrayList<int[][]>();

    private static Map<Integer, int[][]> matrixByPlayers = new HashMap<>();

    static {
        matrixByPlayers.put(2, negativeMatrix1);
        matrixByPlayers.put(3, negativematrix2);
        matrixByPlayers.put(4, negativematrix3);
    }

    public static int[][] getMatrixByPlayers(int players) {
        return matrixByPlayers.getOrDefault(players, null);
    }


    public static int[] returndim(int[][] matrix) {
        int[] dim = new int[2];
        dim[0] = matrix.length;
        dim[1] = matrix[0].length;
        return dim;
    }

    public static void addMatrix(int[][] matrix) {
        negativeMatrixList.add(matrix);
    }

}
