package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartInterface extends JFrame {

    private JButton buttonClient, buttonProduct, buttonOrder;

    public StartInterface() {
        super("Home");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        buttonClient = new JButton("Clients");
        buttonProduct = new JButton("Products");
        buttonOrder = new JButton("Orders");
        buttonClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ClientsInterface();
            }
        });

        buttonProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Product");
            }
        });

        buttonOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Order");
            }
        });

        panel.add(buttonClient);
        panel.add(buttonProduct);
        panel.add(buttonOrder);
        add(panel);

    }

    public static void startHome() {
        StartInterface startInterface = new StartInterface();
        startInterface.setVisible(true);
        startInterface.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        startHome();
    }
}