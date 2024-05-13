package presentation;

import bll.BillsBLL;
import bll.ClientsBLL;
import model.Bills;
import model.Clients;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
/**
 * User Interface to manage Bills, which is controlled by presentation.Control
 */
public class BillsInterface extends JFrame {
    private JLabel jLabel;
    private JTextField clientEmail;
    private JButton button, backButton;
    private JTextArea textArea;

    public BillsInterface() {

        setTitle("Bills");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 300);

        JPanel jPanel = new JPanel(new FlowLayout());

        jLabel = new JLabel("Client Email: ");
        clientEmail = new JTextField(15);
        button = new JButton("Search!");
        backButton = new JButton("BACK");

        jPanel.add(jLabel);
        jPanel.add(clientEmail);
        jPanel.add(button);
        jPanel.add(backButton);

        textArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(textArea);

        add(jPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newEmail = clientEmail.getText();
                ClientsBLL clientsBLL = new ClientsBLL();
                Clients clients = clientsBLL.findClientByEmails(newEmail);
                BillsBLL billsBLL = new BillsBLL();
                List<Bills> newBills = billsBLL.findAllClientBills(clients.getClientId());
                textArea.setText("");
                for (Bills index : newBills) {
                    textArea.append(index.toString() + "\n");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                StartInterface.startHome();
            }
        });

        setVisible(true);

    }
}