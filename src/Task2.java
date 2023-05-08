import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Task2 {
    private JFrame frame;
    private JTextField filePathField;
    private JButton openFileBtn;
    private JTable matrixTable1, matrixTable2;
    private JTextField boolVector;
    private JLabel filePathLabel;
    private JPanel inputPanel;
    public Task2() {
        frame = new JFrame();
        frame.setTitle("Build vector from 2 matrix");
        frame.setLayout(new BorderLayout());

        // Control panel area
        inputPanel = new JPanel();
        filePathLabel = new JLabel("Enter a file");
        filePathField = new JTextField("D:/Java_labs/jlab6-GaliasAndriy/src/matrix.txt", 32);
        openFileBtn = new JButton("Open a file");
        openFileBtn.addActionListener(new OpenButtonListener());
        inputPanel.add(filePathLabel);
        inputPanel.add(filePathField);
        inputPanel.add(openFileBtn);
        inputPanel.setBackground(Color.LIGHT_GRAY);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Matrix area
        matrixTable1 = new JTable();
        matrixTable2 = new JTable();
        matrixTable1.setEnabled(false);
        matrixTable2.setEnabled(false);
        JScrollPane scrollPanel1 = new JScrollPane(matrixTable1);
        JScrollPane scrollPanel2 = new JScrollPane(matrixTable2);
        frame.add(scrollPanel1, BorderLayout.WEST);
        frame.add(scrollPanel2, BorderLayout.EAST);

        // Center a Table
        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
        centerRender.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < matrixTable1.getColumnCount(); i++) {
            matrixTable1.getColumnModel().getColumn(i).setCellRenderer(centerRender);
        }

        JPanel resultPanel = new JPanel();
        // Vector area - result
        boolVector = new JTextField(32);
        resultPanel.add(boolVector);
        frame.add(resultPanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(940, 440);
    }

    private int[] BuildVectorX (int[][] A, int[][] B, int n) {
        int[] X = new int[n];
        int countA = 0, countB = 0, countRes = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++) {
                if (A[i][j] < 0) countA++;
                if (B[i][j] < 0) countB++;
            }
            if (countA == countB && countA != 0) {
                X[i] = 1;
                countA = countB = 0;
            } else {
                X[i] = 0;
                countA = countB = 0;
            }
        }
        //System.out.println("Vector X["+n+"]: ");
        for (int i = 0; i < n; i++)
            System.out.print(X[i] + " ");
        return X;
    }
    public static String vectorToString(int[] vector) {
        StringBuilder sb = new StringBuilder();
        for (int i : vector) {
            sb.append(i + " ");
        }
        return sb.toString();
    }

    private void printMatrix(int[][] a, int[][] b, int size) {
        DefaultTableModel model1 = new DefaultTableModel();
        for (int col = 0; col < size; col++) {
            model1.addColumn(col);
        }
        for (int row = 0; row < size; row++) {
            Integer[] data = new Integer[size];
            for (int col = 0; col < size; col++){
                data[col] = a[row][col];
            }
            model1.addRow(data);
        }
        matrixTable1.setModel(model1);

        DefaultTableModel model2 = new DefaultTableModel();
        for (int col = 0; col < size; col++) {
            model2.addColumn(col);
        }
        for (int row = 0; row < size; row++) {
            Integer[] data = new Integer[size];
            for (int col = 0; col < size; col++){
                data[col] = b[row][col];
            }
            model2.addRow(data);
        }
        matrixTable2.setModel(model2);
    }

    private class OpenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            int[][] A = null;
            int[][] B = null;
            boolean flag = true;
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line = br.readLine();
                int size = Integer.parseInt(line);

                A = new int[size][size];
                B = new int[size][size];
                br.readLine(); // skip 2nd line

                int row = 0;
                while ((line = br.readLine()) != null) {
                    if (line.isEmpty()) { // if line is empty, it means that we're done with reading matrix A
                        flag = false;
                        row = 0;
                        continue;
                    }
                    String[] parts = line.split("\\s+");
                    int col = 0;
                    for (String part : parts) {
                        try {
                            int val = Integer.parseInt(part);
                            if (flag) {
                                A[row][col] = val;
                            } else {
                                B[row][col] = val;
                            }
                            col++;
                        } catch (NumberFormatException err) {
                            throw new NumberFormatException();
                        }
                    }

                    row++;
                }

                printMatrix(A, B, size);
                String v = vectorToString(BuildVectorX(A, B, size));
                boolVector.setText(v);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "File: " + filePath + " not found!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String[] args) {
        new Task2();
    }
}