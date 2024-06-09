package ru.vsu.cs.sibirko_i_s.MLITA.gui;

import ru.vsu.cs.sibirko_i_s.MLITA.logic.FullMatrix;
import ru.vsu.cs.sibirko_i_s.MLITA.logic.Program;
import ru.vsu.cs.sibirko_i_s.MLITA.util.ArrayUtils;
import ru.vsu.cs.sibirko_i_s.MLITA.util.JTableUtils;
import ru.vsu.cs.sibirko_i_s.MLITA.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JFrame {
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JPanel panel12;
    private JPanel panel3;
    private JPanel panel45;
    private JPanel panel6;
    private JButton buttonFindDeterminant;
    private JButton buttonCrammer;
    private JButton buttonClear;
    private JButton buttonLoadFromFile;
    private JButton buttonRandomInput;
    private JTable tableInput;
    private JTable tableOutput;

    private JTable tableInput32;
    private JTable tableInput31;
    private JTable tableOutput3;
    private JButton buttonProduct;
    private JButton buttonSolve4;
    private JButton buttonSolve5;
    private JTable tableInput45;
    private JTable tableOutput45;
    private JButton buttonLoad45;
    private JButton buttonGauss;
    private JTable tableInput6;
    private JButton buttonRandomFor6;
    private JButton buttonLoad6;
    private JButton buttonLoad31;
    private JButton buttonLoad32;
    private JTextPane textPane6;

    private final JFileChooser fileChooserOpen;

    public Menu() {
        this.setTitle("Matrix solver");
        this.setContentPane(panelMain);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 70, true, true, true, true);
        JTableUtils.initJTableForArray(tableOutput, 100, false, true, false, false);

        JTableUtils.initJTableForArray(tableInput31, 70, true, true, true, true);
        JTableUtils.initJTableForArray(tableInput32, 70, true, true, true, true);
        JTableUtils.initJTableForArray(tableOutput3, 80, false, true, false, false);

        JTableUtils.initJTableForArray(tableInput45, 70, true, true, true, true);
        JTableUtils.initJTableForArray(tableOutput45, 100, false, true, false, false);

        JTableUtils.initJTableForArray(tableInput6, 70, true, true, true, true);

        tableOutput.setEnabled(false);
        tableInput.setRowHeight(35);
        tableOutput.setRowHeight(35);

        tableOutput3.setEnabled(false);
        tableInput31.setRowHeight(35);
        tableInput32.setRowHeight(35);
        tableOutput3.setRowHeight(35);

        tableOutput45.setEnabled(false);
        tableInput45.setRowHeight(35);
        tableOutput45.setRowHeight(35);

        tableInput6.setRowHeight(35);

        fileChooserOpen = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File(Program.defaultFileDirectory));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserOpen.setAcceptAllFileFilterUsed(false);

        JTableUtils.writeArrayToJTable(tableInput, new double[5][5]);

        this.pack();

        buttonLoadFromFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    double[][] arr = ArrayUtils.readDoubleArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableInput, arr);
            }
        } catch (Exception e) {
            SwingUtils.showErrorMessageBox(e);
        }
        });

        buttonClear.addActionListener(actionEvent -> {
            try {
                ((DefaultTableModel) tableInput.getModel()).setRowCount(0);
                for (int r = 0; r < tableInput.getRowCount(); r++) {
                    for (int c = 0; c < tableInput.getColumnCount(); c++) {
                        tableInput.setValueAt(0, r, c);
                    }
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonRandomInput.addActionListener(actionEvent -> {
            try {
                int[][] arr = ArrayUtils.createRandomIntMatrix(5, 6, -10, 15);
                JTableUtils.writeArrayToJTable(tableInput, arr);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonFindDeterminant.addActionListener(actionEvent -> {
            try {
                double[][] arr = JTableUtils.readDoubleMatrixFromJTable(tableInput);
                double result = FullMatrix.calculateDeterminant(arr);
                double[] result1 = new double[1];
                result1[0] = result;
                JTableUtils.writeArrayToJTable(tableOutput, result1);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonCrammer.addActionListener(actionEvent -> {
            try {
                double[][] arr = JTableUtils.readDoubleMatrixFromJTable(tableInput);
                double[] res = FullMatrix.findEquationSolutions(arr);
                JTableUtils.writeArrayToJTable(tableOutput, res);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonLoad31.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    double[][] arr = ArrayUtils.readDoubleArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableInput31, arr);
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonLoad32.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    double[][] arr = ArrayUtils.readDoubleArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableInput32, arr);
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonProduct.addActionListener(actionEvent -> {
            try {
                double[][] matrix1 = JTableUtils.readDoubleMatrixFromJTable(tableInput31);
                double[][] matrix2 = JTableUtils.readDoubleMatrixFromJTable(tableInput32);
                double[][] res = FullMatrix.find_Product_Of_Matrices(matrix1, matrix2);
                JTableUtils.writeArrayToJTable(tableOutput3, res);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonSolve4.addActionListener(actionEvent -> {
            try {
                double[][] arr = JTableUtils.readDoubleMatrixFromJTable(tableInput45);
                double[][] res = FullMatrix.findInverseMatrix(arr);
                JTableUtils.writeArrayToJTable(tableOutput45, res);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonSolve5.addActionListener(actionEvent -> {
            try {
                double[][] arr = JTableUtils.readDoubleMatrixFromJTable(tableInput45);
                double[][] res = FullMatrix.methodOfInverseMatrix(arr);
                JTableUtils.writeArrayToJTable(tableOutput45, res);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonLoad45.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    double[][] arr = ArrayUtils.readDoubleArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableInput45, arr);
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonRandomFor6.addActionListener(actionEvent -> {
            try {
                int[][] arr = ArrayUtils.createRandomIntMatrix(5, 6, -10, 15);
                JTableUtils.writeArrayToJTable(tableInput6, arr);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonLoad6.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    double[][] arr = ArrayUtils.readDoubleArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableInput6, arr);
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        buttonGauss.addActionListener(actionEvent -> {
            try {
                double[][] matrix1 = JTableUtils.readDoubleMatrixFromJTable(tableInput6);
                matrix1 = FullMatrix.nullsTriangle(matrix1);
                String str = FullMatrix.MethodGaussa(matrix1);
                textPane6.setText(str);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
    }
}
