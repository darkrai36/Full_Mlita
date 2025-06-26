package ru.vsu.cs.sibirko_i_s.MLITA.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixMethods {
    /**
     * Method for obtaining a square matrix from an augmented one
     * @param matrix an augmented matrix
     * @return square matrix
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
     * Method for obtaining a main matrix from an augmented one
     * @param augmentedMatrix - an augmented matrix
     * @return main matrix
     */
    private static double[][] getSubMatrix(double[][] augmentedMatrix) {
        int rowCount = augmentedMatrix.length;
        int colCount = augmentedMatrix[0].length - 1;
        double[][] mainMatrix = new double[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                mainMatrix[i][j] = augmentedMatrix[i][j];
            }
        }
        return mainMatrix;
    }

    /**
     * Method to check if a string is null
     * @param row row
     * @return True, if row is null. Else returns False
     */
    private static boolean isNullRow(double[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method for reducing a matrix to upper triangular form
     * @param augmentedMatrix an augmented matrix
     */
    private static void getTriangleType(double[][] augmentedMatrix) {
        int size = augmentedMatrix.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                double requiredCoefficient = augmentedMatrix[j][i] / augmentedMatrix[i][i];
                if (Double.isNaN(requiredCoefficient)) {
                    break;
                }
                for (int k = 0; k < augmentedMatrix[0].length; k++) {
                    augmentedMatrix[j][k] -= requiredCoefficient * augmentedMatrix[i][k];
                }
            }
        }
    }

    /**
     * A private method for selecting free members of a system
     * @param matrix original matrix of system
     * @return an array consisting of free members in the form of a matrix (n*1), where <b>n</b> is the number of rows of the original matrix
     */
    private static double[][] takeFreeValues(double[][] matrix) {
        double[][] res = new double[matrix.length][1];
        for (int i = 0; i < matrix.length; i++) {
            res[i][0] = matrix[i][matrix[0].length - 1];
        }
        return res;
    }

    /**
     * Method for randomly filling a matrix
     * @param matrix empty matrix
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
     * Method for printing matrix
     * @param matrix matrix
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
     * Method for finding the rank of a matrix (reduces the matrix to upper triangular form using elementary transformations)
     * @param augmentedMatrix - an augmented matrix
     * @return rank of a matrix
     */
    private static int getRank(double[][] augmentedMatrix) {
        int rank = 0;
        getTriangleType(augmentedMatrix);
        for (double[] row : augmentedMatrix) {
            if (!isNullRow(row)) {
                rank++;
            }
        }
        return rank;
    }

    /**
     * Method to remove zero rows in a matrix
     * @param count number of rows to be deleted
     * @param matrix matrix
     * @return matrix without zero rows
     */
    private static double[][] deleteRows(int count, double[][] matrix) {
        double[][] res = new double[matrix.length - count][matrix[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = matrix[i][j];
            }
        }
        return res;
    }

    /**
     * Method for calling a function that calculates the determinant of a matrix
     * @param matrix an original matrix
     * @return hidden method for calculating the determinant
     */
    public static double calculateDeterminant(double[][] matrix) {
        if (matrix.length == matrix[0].length - 1) {
            return determinant(buildSquareMatrix(matrix));
        }
        return determinant(matrix);
    }

    /**
     * Method of recursive calculation of determinant by row or column
     * @param squareMatrix a square matrix
     * @return a determinant of square matrix
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
     * A method for constructing a new square matrix by deleting the first row of the original matrix
     * @param originalMatrix original matrix
     * @param colToRemove column number to delete
     * @return sub-matrix
     */
    private static double[][] buildNewMatrix(double[][] originalMatrix, int colToRemove) {
        int n = originalMatrix.length - 1;
        double[][] subMatrix = new double[n][n];
        int newCol = 0;
        for (int i = 1; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix[0].length; j++) {
                if (j == colToRemove) {
                    continue;
                }
                subMatrix[i - 1][newCol] = originalMatrix[i][j];
                newCol++;
            }
            newCol = 0;
        }
        return subMatrix;
    }

    /**
     * Method for finding the roots of a system of linear equations (Cramer's method)
     * @param originalMatrix an original matrix
     * @return array with roots of a SoLE
     */
    public static double[] findEquationSolutions(double[][] originalMatrix) {
        double[] roots = new double[originalMatrix[0].length - 1];
        double determinant = calculateDeterminant(originalMatrix);
        for (int i = 0; i < roots.length; i++) {
            roots[i] = calculateDeterminant(buildMatrixForFindingSolutions(originalMatrix, i)) / determinant;
        }
        return roots;
    }

    /**
     * Method for constructing a new matrix to find the determinant of some root of the original matrix
     * @param originalMatrix original matrix
     * @param colToSwap column to replace with the last column of the extended matrix
     * @return matrix for finding the determinant of a certain root
     */
    private static double[][] buildMatrixForFindingSolutions(double[][] originalMatrix, int colToSwap) {
        double[][] newMatrix = new double[originalMatrix.length][originalMatrix[0].length - 1];
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {
                if (j == colToSwap) {
                    newMatrix[i][j] = originalMatrix[i][originalMatrix[0].length - 1];
                } else {
                    newMatrix[i][j] = originalMatrix[i][j];
                }
            }
        }
        return newMatrix;
    }

    /**
     * Method for finding the product of two matrices
     * @param firstMatrix first matrix
     * @param secondMatrix second matrix
     * @return product of two matrices
     */
    public static double[][] findProductOfMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        if (firstMatrix[0].length != secondMatrix.length) {
            throw new IllegalStateException("This action is impossible, your matrices are incorrect.");
        }
        return productOfMatrices(firstMatrix, secondMatrix);
    }

    /**
     * Method for finding the product of matrices (private)
     * @param first first matrix
     * @param second second matrix
     * @return product of two matrices
     */
    private static double[][] productOfMatrices(double[][] first, double[][] second) {
        double[][] res = new double[first.length][second[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                for (int k = 0; k < second.length; k++) {
                    res[i][j] += first[i][k] * second[k][j];
                }
            }
        }
        return res;
    }

    /**
     * Method for finding the inverse of a matrix (if the determinant is not zero and the matrix is square)
     * @param originalMatrix an original matrix
     * @return calls a private method to construct the inverse matrix if the conditions for its existence are met
     */
    public static double[][] findInverseMatrix(double[][] originalMatrix) {
        if (originalMatrix.length != originalMatrix[0].length) {
            throw new IllegalArgumentException("Main matrix must be square.");
        }
        if (calculateDeterminant(originalMatrix) == 0) {
            throw new IllegalArgumentException("Determinant equals 0, so we can't find inverse matrix.");
        }
        return inverseMatrix(originalMatrix);
    }

    /**
     * Private method for finding the inverse matrix
     * @param originalMatrix an original matrix
     * @return an inverse matrix
     */
    private static double[][] inverseMatrix(double[][] originalMatrix) {
        double determinant = calculateDeterminant(originalMatrix);
        double[][] res = new double[originalMatrix.length][originalMatrix[0].length - 1];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = (1/determinant) * transposedMatrix(buildMatrixWithAlgebraicComplements(originalMatrix))[i][j];
            }
        }
        return res;
    }

    /**
     * Method of constructing a matrix for finding an algebraic complement
     * @param originalMatrix main matrix
     * @param rowToRemove row to be deleted
     * @param colToRemove column to be deleted
     * @return auxiliary matrix
     */
    private static double[][] buildMatrixForAlgebraicComplements(double[][] originalMatrix, int rowToRemove, int colToRemove) {
        double[][] res = new double[originalMatrix.length - 1][originalMatrix[0].length - 1];
        int newRow = 0;
        int newCol = 0;

        for (int i = 0; i < originalMatrix.length; i++) {
            if (i == rowToRemove) {
                continue;
            }
            for (int j = 0; j < originalMatrix[0].length; j++) {
                if (j == colToRemove) {
                    continue;
                }
                res[newRow][newCol] = originalMatrix[i][j];
                newCol++;
            }
            newRow++;
            newCol = 0;
        }
        return res;
    }

    /**
     * Method of constructing a matrix from already found algebraic complements
     * @param originalMatrix an original matrix
     * @return matrix of algebraic complements
     */
    private static double[][] buildMatrixWithAlgebraicComplements(double[][] originalMatrix) {
        double[][] res = new double[originalMatrix.length][originalMatrix[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = Math.pow(-1, i + j) * calculateDeterminant(buildMatrixForAlgebraicComplements(originalMatrix, i, j));
                if (res[i][j] == -0) {
                    res[i][j] = 0;
                }
            }
        }
        return res;
    }

    /**
     * Method for constructing a transposed matrix
     * @param originalMatrix an original matrix
     * @return transposed matrix
     */
    private static double[][] transposedMatrix(double[][] originalMatrix) {
        int rows = originalMatrix.length;
        int cols = originalMatrix[0].length;
        double[][] res = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res[j][i] = originalMatrix[i][j];
            }
        }
        return res;
    }

    /**
     * Method to call a private method that solves a matrix using the inverse matrix method. Necessary checks are present
     * @param originalMatrix an original matrix
     * @return After necessary checks calls a private method to solve the system
     */
    public static double[][] methodOfInverseMatrix(double[][] originalMatrix) {
        if (calculateDeterminant(originalMatrix) == 0) {
            throw new NullPointerException("Equation hasn't solves.");
        }
        return findSolutionsWithInverseMatrix(originalMatrix);
    }

    /**
     * Inverse matrix method
     * @param originalMatrix an original matrix
     * @return solutions of a system of linear equations
     */
    private static double[][] findSolutionsWithInverseMatrix(double[][] originalMatrix) {
        double[][] freeValues = takeFreeValues(originalMatrix);
        double[][] inverseMatrix = inverseMatrix(originalMatrix);
        return findProductOfMatrices(inverseMatrix, freeValues);
    }

//    /*public static List<Double> solveWithGauss(double[][] mainMatrix) throws Exception {
//        List<Double> res = new ArrayList<>();
//        double[][] subMatrix = getSubMatrix(mainMatrix);
//        int rankMain = getRank(mainMatrix);
//        int rankSub = getRank(subMatrix);
//        if (rankSub == rankMain && rankSub == subMatrix[0].length) {
//            return findEquations(mainMatrix).reversed();
//        } else if (rankSub != rankMain) { //Уравнение не имеет ни одного решения
//            return res;
//        }
//        //Случай, когда уравнение имеет бесконечно много решений
//        ArrayList<String> temp = new ArrayList<>();
//
//    }*/


    private static List<Double> findEquations(double[][] matrix) {
        List<Double> res = new ArrayList<>();
        res.add(matrix[matrix.length - 1][matrix[0].length - 1] / matrix[matrix.length - 1][matrix[0].length - 2]);
        for (int i = matrix.length - 2; i >= 0; i--) {
            double sum_of_free = getSumOfFreeCoefficients(matrix, i, res);
            res.add(sum_of_free / matrix[i][i]);
        }
        return res;
    }
    private static double getSumOfFreeCoefficients(double[][] matrix, int rowNumber, List<Double> equations) {
        double res = matrix[rowNumber][matrix[0].length - 1];
        for (int j = matrix[0].length - 2; j > rowNumber; j--) {
            res -= matrix[rowNumber][j] * equations.get(matrix[0].length - 2 - j);
        }
        return res;
    }

    public static double[][] nullsTriangle(double[][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int col = 0; col < cols; col++){
            for (int row = col + 1; row < rows; row++){
                if (matrix[col][col] != 0) {
                    double k = - (matrix[row][col] / matrix[col][col]);
                    matrix = subtraction(matrix, row, k, col);
                }
            }
        }
        return matrix;
    }
    public static double[][] subtraction(double[][] matrix, int r, double k, int col1){
        for (int col = 0; col < matrix[0].length; col++){
            matrix[r][col] += k * matrix[col1][col];
        }
        return matrix;
    }

    public static double[][] rootsByGauss(double[][] matrix) {
        int freeXs = getFreeVariables(matrix);
        int bazisXs = matrix.length - freeXs;
        double[][] roots = new double[bazisXs][freeXs + 1];
        for (int row = bazisXs - 1; row >= 0; row--) {
            roots[row][0] = matrix[row][matrix[0].length - 1] / matrix[row][row];
            for (int dob = matrix.length - 1 - freeXs; dob > row; dob--) {
                roots[row][0] += (-matrix[row][dob] * roots[dob][0]) / matrix[row][row];
            }
            for (int i = 1; i < roots[0].length; i++) {
                roots[row][i] = -matrix[row][bazisXs - 1 + i] / matrix[row][row];
                for (int dob = matrix.length - 1 - freeXs; dob > row; dob--) {
                    roots[row][i] += (-matrix[row][dob] * roots[dob][i]) / matrix[row][row];
                }
            }
        }
        return roots;
    }
    public static String MethodGaussa(double[][] matrix) {
        int fv = getFreeVariables(matrix);
        int bv = matrix.length - fv;
        String result;
        StringBuilder line = new StringBuilder();
        double[][] roots2 = rootsByGauss(matrix);
        for (int row = 0; row < roots2.length; row++) {
            line.append("X" + (row + 1) + " = " + String.format("%.3f", roots2[row][0]));
            for (int col = 1; col < roots2[0].length; col++) {
                line.append(" + (" + String.format("%.3f", roots2[row][col]) + ")*X" + (col + bv));
            }
            line.append("\n");
        }
        result = line.toString();
        return result;
    }
    public static int getFreeVariables(double[][] matrix) { //свободные переменные
        int freeVariable = matrix.length;
        for (int row = matrix.length - 1; row >= 0; row--) {
            if (rowOfZeros(matrix[row])) {
                freeVariable = row;
            }
        }
        return matrix.length - freeVariable;
    }
    public static boolean rowOfZeros(double[] row) {
        for (double element : row) {
            if (element != 0) {
                return false;
            }
        }
        return true;
    }
}