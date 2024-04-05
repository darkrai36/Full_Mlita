package ru.vsu.cs.sibirko_i_s.MLITA.logic;

import java.util.Random;

public class FullMatrix {
    public final int height;
    public final int weight;
    public int[][] matrix;

    public FullMatrix(int height, int weight) {
        if (height > 0 && weight > 0) {
            this.height = height;
            this.weight = weight;
            matrix = new int[height][weight];
        } else {
            throw new NullPointerException("Matrix is empty, try again");
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public void randomInput() {
        Random rnd = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = rnd.nextInt(50);
            }
        }
    }
    public void printMatrix() {
        System.out.println("Matrix: ");
        for (int[] rows : matrix) {
            for (int value : rows) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
