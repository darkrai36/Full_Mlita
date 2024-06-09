package ru.vsu.cs.sibirko_i_s.MLITA.logic;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import java.util.ArrayList;
import java.util.List;
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
     * Метод для получения основной матрицы из расширенной
     * @param mainMatrix - расширенная матрица
     * @return основную матрицу
     */
    private static double[][] getSubMatrix(double[][] mainMatrix) {
        int rowCount = mainMatrix.length;
        int colCount = mainMatrix[0].length - 1;
        double[][] res = new double[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                res[i][j] = mainMatrix[i][j];
            }
        }
        return res;
    }
    /**
     * Метод, проверяющий, является ли строка нулевой
     * @param arr - собственно, сама строка
     * @return true or false
     */
    private static boolean isNullRow(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * Метод для приведения матрицы к верхнетреугольному виду
     * @param mainMatrix расширенная матрица системы
     */
    private static void getTriangleType(double[][] mainMatrix) {
        int size = mainMatrix.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                double requiredCoefficient = mainMatrix[j][i] / mainMatrix[i][i];
                if (Double.isNaN(requiredCoefficient)) {
                    break;
                }
                for (int k = 0; k < mainMatrix[0].length; k++) {
                    mainMatrix[j][k] -= requiredCoefficient * mainMatrix[i][k];
                }
            }
        }
    }
    /**
     * Приватный метод для отбора свободных членов системы
     * @param matrix исходная матрица
     * @return массив, состоящий из свободных членов в виде матрицы (n*1), где n - количество строк исходной матрицы
     */
    private static double[][] takeFreeValues(double[][] matrix) {
        double[][] res = new double[matrix.length][1];
        for (int i = 0; i < matrix.length; i++) {
            res[i][0] = matrix[i][matrix[0].length - 1];
        }
        return res;
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
     * @param matrix основная матрица
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
     * Метод для нахождения ранга матрицы (приводит матрицу к верхнетреугольному виду с помощью элементарных преобразований)
     * @param mainMatrix - расширенная матрица
     * @return ранг матрицы
     */
    private static int getRank(double[][] mainMatrix) {
        int rank = 0;
        getTriangleType(mainMatrix);
        for (double[] row : mainMatrix) {
            if (!isNullRow(row)) {
                rank++;
            }
        }
        return rank;
    }
    /**
     * Метод для удаления нулевых строк в матрице
     * @param count - количество удаляемых строк
     * @param matrix матрица
     * @return матрицу без нулевых строк
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
     * @param squareMatrix квадратная матрица
     * @return вызывает приватный метод для расчёта определителя
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

    /**
     * Метод для поиска обратной матрицы (если определитель не равен нулю и матрица квадратная)
     * @param mainMatrix основная матрица
     * @return вызывает приватный метод для построения обратной матрицы, если выполняются условия её существования
     */
    public static double[][] findInverseMatrix(double[][] mainMatrix) {
        if (mainMatrix.length != mainMatrix[0].length) {
            throw new IllegalArgumentException("Main matrix must be square.");
        }
        if (calculateDeterminant(mainMatrix) == 0) {
            throw new IllegalArgumentException("Determinant equals 0, so we can't find inverse matrix.");
        }
        return inverseMatrix(mainMatrix);
    }

    /**
     * Приватный метод для нахождения обратной матрицы
     * @param mainMatrix основная матрица
     * @return обратную матрицу
     */
    private static double[][] inverseMatrix(double[][] mainMatrix) {
        double determinant = calculateDeterminant(mainMatrix);
        double[][] res = new double[mainMatrix.length][mainMatrix[0].length - 1];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = (1/determinant) * transposedMatrix(buildMatrixWithAlgebraicComplements(mainMatrix))[i][j];
            }
        }
        return res;
    }

    /**
     * Метод построения матрицы для нахождения алгебраического дополнения
     * @param mainMatrix основная матрица
     * @param rowToRemove зачеркиваемая строка
     * @param colToRemove зачеркиваемый столбец
     * @return вспомогательная матрица (как при нахождении определителя)
     */
    private static double[][] buildMatrixForAlgebraicComplements(double[][] mainMatrix, int rowToRemove, int colToRemove) {
        double[][] res = new double[mainMatrix.length - 1][mainMatrix[0].length - 1];
        int newRow = 0;
        int newCol = 0;

        for (int i = 0; i < mainMatrix.length; i++) {
            if (i == rowToRemove) {
                continue;
            }
            for (int j = 0; j < mainMatrix[0].length; j++) {
                if (j == colToRemove) {
                    continue;
                }
                res[newRow][newCol] = mainMatrix[i][j];
                newCol++;
            }
            newRow++;
            newCol = 0;
        }
        return res;
    }

    /**
     * Метод построения матрицы из уже найденных алгебраических дополнений
     * @param mainMatrix основная матрица
     * @return матрица алгебраических дополнений
     */
    private static double[][] buildMatrixWithAlgebraicComplements(double[][] mainMatrix) {
        double[][] res = new double[mainMatrix.length][mainMatrix[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = Math.pow(-1, i + j) * calculateDeterminant(buildMatrixForAlgebraicComplements(mainMatrix, i, j));
                if (res[i][j] == -0) {
                    res[i][j] = 0;
                }
            }
        }
        return res;
    }

    /**
     * Метод для построения транспонированной матрицы
     * @param matrix основная матрица
     * @return транспонированная матрица
     */
    private static double[][] transposedMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] res = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res[j][i] = matrix[i][j];
            }
        }
        return res;
    }

    /**
     * Метод для вызова приватного метода, который решает матрицу методом обратной матрицы. Присутствуют необходимые проверки
     * @param mainMatrix основная матрица
     * @return вызывает приватный метод для решения системы
     */
    public static double[][] methodOfInverseMatrix(double[][] mainMatrix) {
        if (calculateDeterminant(mainMatrix) == 0) {
            throw new NullPointerException("Equation hasn't solves.");
        }
        return findSolutionsWithInverseMatrix(mainMatrix);
    }

    /**
     * Метод обратной матрицы
     * @param matrix основная матрица
     * @return решения системы линейных уравнений
     */
    private static double[][] findSolutionsWithInverseMatrix(double[][] matrix) {
        double[][] freeValues = takeFreeValues(matrix);
        double[][] inverseMatrix = inverseMatrix(matrix);
        return find_Product_Of_Matrices(inverseMatrix, freeValues);
    }

    /**
     * Метод для поиска решений СЛУ методом Гаусса
     * @param mainMatrix - расширенная матрица системы
     * @return решения системы
     */
    public static List<Double> solveWithGauss(double[][] mainMatrix) throws Exception {
        List<Double> res = new ArrayList<>();
        double[][] subMatrix = getSubMatrix(mainMatrix);
        int rankMain = getRank(mainMatrix);
        int rankSub = getRank(subMatrix);
        if (rankSub == rankMain && rankSub == subMatrix[0].length) {
            return findEquations(mainMatrix).reversed();
        } else if (rankSub != rankMain) { //Уравнение не имеет ни одного решения
            return res;
        }
        //Случай, когда уравнение имеет бесконечно много решений
        ArrayList<String> temp = new ArrayList<>();

    }


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

    public static void main(String[] args) throws Exception {
        /*Matrix matrix = new Matrix(10, 10);
        Random random = new Random();

        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10 ; j++)
                matrix.set(i, j, random.nextDouble());


        EigenvalueDecomposition eig = matrix.eig();

// Собственные вектора
        Matrix v = eig.getV();
        double[][] res = v.getArray();

// Реальная часть собственных значений
        double[] realEigenvalues = eig.getRealEigenvalues();

// Мнимая часть собственных значений
        double[] imagEigenvalues = eig.getImagEigenvalues();

        System.out.println("Собственные вектора: ");
        for (int i = 0; i < v.getArray().length; i++) {
            System.out.println(res[i][0] + " ");
        }
        System.out.println("Реальная часть собственных значений");
        for (int i = 0; i < realEigenvalues.length; i++) {
            System.out.print(realEigenvalues[i] + " ");
        }
        System.out.println();
        System.out.println("Мнимая часть собственных значений: ");
        for (int i = 0; i < imagEigenvalues.length; i++) {
            System.out.print(imagEigenvalues[i] + " ");
        }*/
        double[][] arr = {{1, -2, 0, 1, -3}, {3, -1, -2, 0, 1}, {2, 1, -2, -1, 4}, {1, 3, -2, -2, 7}};
        /*double[][] arr = {{2, -1, -1, -3}, {1, -1, 2, 5}, {1, 1, 1, 6}};*/
        /*double[][] arr = {{2, -3, 1, 7}, {3, 2, -1, 5}, {4, 7, -3, 4}};*/

        solveWithGauss(arr);
    }
}