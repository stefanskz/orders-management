package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductsInterface extends JFrame {
    private JTextField[] inData;
    private JButton[] buttons;
    private JTable productsTable;

    String[] colNames = {"Product Id", "Product Name", "Price", "Quantity"};

    public ProductsInterface() {
        setTitle("Products");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 350);
        setResizable(false);

        JPanel jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        inData = new JTextField[colNames.length];

        for (int i = 0; i < colNames.length; i++) {
            JLabel jLabel = new JLabel(colNames[i] + ": ");
            constraints.gridx = 0;
            constraints.gridy = i;
            jPanel.add(jLabel, constraints);
            JTextField text = new JTextField(10);
            constraints.gridx = 1;
            jPanel.add(text, constraints);
            inData[i] = text;
        }

        String[] nameButtons = {"ADD", "EDIT", "DELETE", "BACK"};
        buttons = new JButton[nameButtons.length];
        for (int i = 0; i < nameButtons.length; i++) {
            JButton jButton = new JButton(nameButtons[i]);
            jButton.setPreferredSize(new Dimension(100, 30));
            constraints.gridx = i % 2;
            constraints.gridy = colNames.length + i / 2;
            constraints.gridwidth = 1;
            jPanel.add(jButton, constraints);
            buttons[i] = jButton;
        }
        JPanel main = new JPanel(new BorderLayout());
        main.add(jPanel, BorderLayout.NORTH);

        String[][] tableData = Control.updateProductsTable();
        productsTable = new JTable(tableData, colNames);

        JScrollPane scroll = new JScrollPane(productsTable);
        main.add(scroll, BorderLayout.CENTER);

        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = inData[1].getText();
                Double newPrice = Double.valueOf(inData[2].getText());
                int newQuantity = Integer.parseInt(inData[3].getText());
                Control.addInProductsTable(newName, newPrice, newQuantity);
                updateProductsTable(Control.updateProductsTable());
            }
        });

        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newId = Integer.parseInt(inData[0].getText());
                String newName = inData[1].getText();
                Double newPrice = Double.valueOf(inData[2].getText());
                int newQuantity = Integer.parseInt(inData[3].getText());
                Control.updateInTableProducts(newId, newName, newPrice, newQuantity);
                updateProductsTable(Control.updateProductsTable());
            }
        });

        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newId = Integer.parseInt(inData[0].getText());
                Control.deleteFromProductsTable(newId);
                updateProductsTable(Control.updateProductsTable());
            }
        });

        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                StartInterface.startHome();
            }
        });

        add(main);
        setVisible(true);

    }

    private void updateProductsTable(String[][] data) {
        DefaultTableModel newData = new DefaultTableModel(data, colNames);
        productsTable.setModel(newData);
        setVisible(true);
    }

}