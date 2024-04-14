package ru.vsu.cs.sibirko_i_s.MLITA.logic;

import java.util.Random;

public class FullMatrix {
    /**
     * Метод для создания квадратной матрицы из расширенной
     * @param matrix расширенная матрица
     * @return квадратную матрицу
     */
    public static int[][] buildSquareMatrix(int[][] matrix) {
        int height = matrix.length;
        int weight = matrix[0].length;
        if (height == 0 || weight == 0) {
            throw new NullPointerException("Invalid matrix's parameters");
        } else if (height != weight - 1) {
            throw new NullPointerException("Row's count must be = (col's count - 1)");
        } else {
            int[][] squareMatrix = new int[height][weight - 1];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < weight - 1; j++) {
                    squareMatrix[i][j] = matrix[i][j];
                }
            }
            return squareMatrix;
        }
    }

    /**
     * Метод для рандомного заполнения матрицы
     * @param matrix пустая матрица
     */
    public static void randomInput(int[][] matrix) {
        Random rnd = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = rnd.nextInt(50);
            }
        }
    }

    /**
     * "Распечатыватель" матрицы
     * @param matrix
     */
    public static void printMatrix(int[][] matrix) {
        System.out.println("Matrix: ");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Метод для вызова функции, вычисляющей определитель матрицы
     * @param matrix исходная матрица
     * @return скрытый метод для расчёта определителя
     */
    public static long calculateDeterminant(int[][] matrix) {
        if (matrix.length == matrix[0].length - 1) {
            return determinant(buildSquareMatrix(matrix));
        }
        return determinant(matrix);
    }

    /**
     * Метод рекурсивного расчёта детерминанта по строке или столбцу
     * @param squareMatrix
     * @return
     */
    private static long determinant(int[][] squareMatrix) {
        long res = 0;
        int height = squareMatrix.length;
        if (height == 1) {
            return squareMatrix[0][0];
        } else if (height == 2) {
            return squareMatrix[0][0] * squareMatrix[1][1] - squareMatrix[0][1] * squareMatrix[1][0];
        } else {
            for (int i = 0; i < squareMatrix[0].length; i++) {
                res += Math.pow(-1, i) * squareMatrix[0][i] * determinant(buildNewMatrix(squareMatrix, i));
            }
        }
        return res;
    }

    /**
     * "Строитель" новой квадратной матрицы посредством вычеркивания первой строки исходной матрицы
     * @param oldMatrix исходная матрица
     * @param colToRemove столбец, который необходимо удалить
     * @return укороченная матрица
     */
    private static int[][] buildNewMatrix(int[][] oldMatrix, int colToRemove) {
        int n = oldMatrix.length - 1;
        int[][] res = new int[n][n];
        int newCol = 0;
        for (int i = 1; i < oldMatrix.length; i++) {
            for (int j = 0; j < oldMatrix[0].length; j++) {
                if (j == colToRemove) {
                    continue;
                }
                res[i - 1][newCol] = oldMatrix[i][j];
                newCol++;
            }
            newCol = 0;
        }
        return res;
    }
}