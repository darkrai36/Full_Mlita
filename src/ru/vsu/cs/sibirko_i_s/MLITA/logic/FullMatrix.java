package ru.vsu.cs.sibirko_i_s.MLITA.logic;

import java.util.Random;

public class FullMatrix {
    private int height;
    private int weight;
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

    public int[][] buildSquareMatrix(FullMatrix object) {
        if (height <= 0 || weight <= 0 || height < weight - 1) {
            throw new NullPointerException("Invalid matrix's parameters");
        } else {
            int[][] squareMatrix = new int[height][weight - 1];
            for (int i = 0; i < object.matrix.length; i++) {
                for (int j = 0; j < object.matrix[0].length - 1; j++) {
                    squareMatrix[i][j] = object.matrix[i][j];
                }
            }
            return squareMatrix;
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
