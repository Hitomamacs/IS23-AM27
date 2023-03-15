package org.example;

import java.util.ArrayList;
import java.util.List;

public class NegativeM {
    static final int[][] negativeMatrix1= {
            {0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 0, 0},

    };
    static final List<int[][]> negativeMatrixList = new ArrayList<int[][]>();


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
