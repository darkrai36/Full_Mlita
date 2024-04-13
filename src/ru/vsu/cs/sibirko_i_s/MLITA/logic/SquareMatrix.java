package ru.vsu.cs.sibirko_i_s.MLITA.logic;

public class SquareMatrix {
    private FullMatrix mainMatrix;
    private int[][] squareMatrix;

    public SquareMatrix(FullMatrix mainMatrix) {
        if (mainMatrix.getHeight() > 0 && mainMatrix.getHeight() == mainMatrix.getWeight() - 1) {
            squareMatrix = mainMatrix.buildSquareMatrix(mainMatrix);
        } else {
            throw new NullPointerException("The main matrix is incorrect. Try again.");
        }
    }
    public void printSquareMatrix() {
        System.out.println("Square matrix: ");
        for (int[] rows : squareMatrix) {
            for (int value : rows) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
    public double calculateDeterminant() {
        return determinant(squareMatrix);
    }
    private double determinant(int[][] squareMatrix) {
        double res = 0;
        if (squareMatrix.length == 1) {
            return squareMatrix[0][0];
        } else if (squareMatrix.length == 2) {
            return (squareMatrix[0][0] * squareMatrix[1][1] - squareMatrix[0][1] * squareMatrix[1][0]);
        } else {
            for (int i = 0; i < squareMatrix[0].length; i++) {
                res += Math.pow(-1, i) * squareMatrix[0][i] * determinant(buildNewSquareMatrix(squareMatrix, i));
            }
        }
        return res;
    }
    private int[][] buildNewSquareMatrix(int[][] oldMatrix, int colToRemove) {
        int[][] newMatrix = new int[oldMatrix.length - 1][oldMatrix[0].length - 1];
        int newCol = 0;
        for (int i = 1; i < oldMatrix.length; i++) {
            for (int j = 0; j < oldMatrix[0].length; j++) {
                if (j == colToRemove) {
                    continue;
                }
                newMatrix[i - 1][newCol] = oldMatrix[i][j];
                newCol++;
            }
            newCol = 0;
        }
        return newMatrix;
    }
}