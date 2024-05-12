package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientsInterface extends JFrame {

    private JTextField[] inData;
    private JButton[] buttons;

    private JTable clientsTable;

    String[] colNames = {"Client Id", "Name", "Email"};

    public ClientsInterface() {
        setTitle("Clients");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 300);
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

        String[][] tableData = Control.updateClientsTable();
        clientsTable = new JTable(tableData, colNames);

        JScrollPane scroll = new JScrollPane(clientsTable);
        main.add(scroll, BorderLayout.CENTER);

        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                StartInterface.startHome();
            }
        });

        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = inData[1].getText();
                String newEmail = inData[2].getText();
                Control.addInTable(newName, newEmail);
                updateTable(Control.updateClientsTable());
            }
        });

        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newId = Integer.parseInt(inData[0].getText());
                String newName = inData[1].getText();
                String newEmail = inData[2].getText();
                Control.updateInTable(newId, newName, newEmail);
                updateTable(Control.updateClientsTable());
            }
        });

        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newId = Integer.parseInt(inData[0].getText());
                Control.deleteFromTable(newId);
                updateTable(Control.updateClientsTable());
            }
        });

        add(main);
        setVisible(true);

    }

    private void updateTable(String[][] data) {
        DefaultTableModel newData = new DefaultTableModel(data, colNames);
        clientsTable.setModel(newData);
        setVisible(true);
    }

}