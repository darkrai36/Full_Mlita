package ru.vsu.cs.sibirko_i_s.MLITA.logic;

import ru.vsu.cs.sibirko_i_s.MLITA.util.ArrayUtils;

public class Test {
    public static void main(String[] args) {
        /*double[][] matrix = ArrayUtils.readDoubleArray2FromFile("C:\\Users\\игарёк\\Desktop\\матрицы\\5.txt");
        FullMatrix.printMatrix(matrix);
        System.out.println("--------------------------------");

        double[] solutions = FullMatrix.findEquationSolutions(matrix);
        printDoubleArr(solutions);
        System.out.println("--------------------------------");

        double[][] matrix1 = ArrayUtils.readDoubleArray2FromFile("C:\\Users\\игарёк\\Desktop\\матрицы\\матрица 1.txt");
        double[][] matrix2 = ArrayUtils.readDoubleArray2FromFile("C:\\Users\\игарёк\\Desktop\\матрицы\\матрица 2.txt");


        printDoubleMatrix(FullMatrix.find_Product_Of_Matrices(matrix1, matrix2));
        System.out.println("---------------------------------");


        double[][] matrix3 = FullMatrix.findInverseMatrix(matrix1);
        printDoubleMatrix(matrix3);
        System.out.println("------------------------");
        printDoubleMatrix(FullMatrix.find_Product_Of_Matrices(matrix1, matrix3));
        */
        /*double[][] matrix = */
    }

    public static void printDoubleArr(double[] arr) {
        for (double value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }



    public static void printDoubleMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}