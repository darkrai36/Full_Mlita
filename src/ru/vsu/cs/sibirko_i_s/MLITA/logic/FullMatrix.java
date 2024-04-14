package ru.vsu.cs.sibirko_i_s.MLITA.logic;

import java.util.Random;

public class FullMatrix {
    /**
     * Метод для создания квадратной матрицы из расширенной
     * @param matrix расширенная матрица
     * @return квадратную матрицу
     */
    public static double[][] buildSquareMatrix(double[][] matrix) {
        int height = matrix.length;
        int weight = matrix[0].length;
        if (height == 0 || weight == 0) {
            throw new NullPointerException("Invalid matrix's parameters");
        } else if (height != weight - 1) {
            throw new NullPointerException("Row's count must be = (col's count - 1)");
        } else {
            double[][] squareMatrix = new double[height][weight - 1];
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
    public static void randomInput(double[][] matrix) {
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
    public static void printMatrix(double[][] matrix) {
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
    public static double calculateDeterminant(double[][] matrix) {
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
    private static double determinant(double[][] squareMatrix) {
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
    private static double[][] buildNewMatrix(double[][] oldMatrix, int colToRemove) {
        int n = oldMatrix.length - 1;
        double[][] res = new double[n][n];
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

    /**
     * Метод для нахождения корней системы линейных уравнений (в простонародье метод Крамера)
     * @param matrix исходная матрица
     * @return массив с корнями уравнения
     */
    public static double[] findEquationSolutions(double[][] matrix) {
        double[] res = new double[matrix[0].length - 1];
        double determinant = calculateDeterminant(matrix);
        for (int i = 0; i < res.length; i++) {
            res[i] = calculateDeterminant(buildMatrixForSolutions(matrix, i)) / determinant;
        }
        return res;
    }

    /**
     * "Строитель" новой матрицы для нахождения определителя некоторого корня исходной матрицы
     * @param matrix исходная матрица
     * @param colToSwap столбец для замены на последний столбец расширенной матрицы
     * @return матрицу для нахождения определителя определенного корня
     */
    private static double[][] buildMatrixForSolutions(double[][] matrix, int colToSwap) {
        double[][] res = new double[matrix.length][matrix[0].length - 1];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                if (j == colToSwap) {
                    res[i][j] = matrix[i][matrix[0].length - 1];
                } else {
                    res[i][j] = matrix[i][j];
                }
            }
        }
        return res;
    }

    /**
     * Метод для нахождения произведения двух матриц
     * @param matrix1 первая матрица
     * @param matrix2 вторая матрица
     * @return произведение
     */
    public static double[][] find_Product_Of_Matrices(double[][] matrix1, double[][] matrix2) {
        if (matrix1[0].length != matrix2.length) {
            throw new IllegalStateException("This action is impossible, your matrices are incorrect.");
        }
        return product_of_matrices(matrix1, matrix2);
    }

    /**
     * Метод нахождения произведения матриц (заприваченный)
     * @param matrix1 первая матрица
     * @param matrix2 вторая матрица
     * @return произведение матриц
     */
    private static double[][] product_of_matrices(double[][] matrix1, double[][] matrix2) {
        double[][] res = new double[matrix1.length][matrix2[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                for (int k = 0; k < matrix2.length; k++) {
                    res[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return res;
    }
}