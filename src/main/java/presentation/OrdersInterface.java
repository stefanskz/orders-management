package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;
import bll.*;

public class OrdersInterface extends JFrame {
    private JTextField clientIdText, clientNameText;
    private JButton searchId, searchName;

    private JTextField productIdText, productNameText, quantityText;
    private JButton searchProductId, searchProductName, orderButton;

    private JTextField clientRez, productRez, orderRez;

    private JButton backButton;

    private Products orderedProduct;

    private int finalId;

    public OrdersInterface() {
        setTitle("Orders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 450);
        setResizable(false);

        orderedProduct = new Products();

        JPanel main = new JPanel(new BorderLayout());

        JPanel row = new JPanel(new GridLayout(9, 1));

        clientIdText = new JTextField(10);
        clientNameText = new JTextField(10);
        searchId = new JButton("Search!");
        searchName = new JButton("Search!");
        productIdText = new JTextField(10);
        productNameText = new JTextField(10);
        quantityText = new JTextField(10);
        searchProductId = new JButton("Search");
        searchProductName = new JButton("Search!");
        orderButton = new JButton("Order!");

        addLine("Client Id:    ", clientIdText, searchId, row);
        addLine("Client Email:  ", clientNameText, searchName, row);
        JPanel textRow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clientRez = new JTextField(30);
        textRow1.add(clientRez);
        row.add(textRow1);
        addLine("Product Id:   ", productIdText, searchProductId, row);
        addLine("Product Name: ", productNameText, searchProductName, row);
        JPanel textRow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        productRez = new JTextField(30);
        textRow2.add(productRez);
        row.add(textRow2);
        addLine("Quantity:     ", quantityText, orderButton, row);
        JPanel textRow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        orderRez = new JTextField(30);
        textRow3.add(orderRez);
        row.add(textRow3);
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("BACK");
        buttonRow.add(backButton);
        row.add(buttonRow);

        searchId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newId = Integer.parseInt(clientIdText.getText());
                ClientsBLL clientsBLL = new ClientsBLL();
                Clients clients = clientsBLL.findClientById(newId);
                finalId = clients.getClientId();
                clientRez.setText(clients.getClientId() + " // " + clients.getName() + " // " + clients.getEmail());
            }
        });

        searchName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = clientNameText.getText();
                ClientsBLL clientsBLL = new ClientsBLL();
                Clients clients = clientsBLL.findClientByEmails(newName);
                finalId = clients.getClientId();
                clientRez.setText(clients.getClientId() + " // " + clients.getName() + " // " + clients.getEmail());
            }
        });

        searchProductId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newProductId = Integer.parseInt(productIdText.getText());
                ProductsBLL productsBLL = new ProductsBLL();
                orderedProduct = productsBLL.findProductById(newProductId);
                productRez.setText(orderedProduct.getProductId() + " // " + orderedProduct.getProductName() + " // " + orderedProduct.getPrice() + " // " + orderedProduct.getProductQuantity());
            }
        });

        searchProductName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newProductName = productNameText.getText();
                ProductsBLL productsBLL = new ProductsBLL();
                orderedProduct = productsBLL.findProductsByName(newProductName);
                productRez.setText(orderedProduct.getProductId() + " // " + orderedProduct.getProductName() + " // " + orderedProduct.getPrice() + " // " + orderedProduct.getProductQuantity());
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newQuantity = Integer.parseInt(quantityText.getText());
                if (newQuantity > orderedProduct.getProductQuantity()) {
                    orderRez.setText("Not Enough Products!");
                } else {
                    OrdersBLL ordersBLL = new OrdersBLL();
                    Orders orders = new Orders(0, finalId, orderedProduct.getProductId(), newQuantity);
                    int newOrdersId = ordersBLL.insertOrders(orders);
                    ProductsBLL productsBLL = new ProductsBLL();
                    orderedProduct.setProductQuantity(orderedProduct.getProductQuantity() - newQuantity);
                    productsBLL.updateProduct(orderedProduct);
                    Bills bills = new Bills(newOrdersId, finalId, orderedProduct.getProductId(), newQuantity, newQuantity * orderedProduct.getPrice());
                    BillsBLL billsBLL = new BillsBLL();
                    billsBLL.insertBills(bills);
                    orderRez.setText("Success!");
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

        main.add(row, BorderLayout.CENTER);

        add(main);
        setVisible(true);

    }

    public void addLine(String newLabel, JTextField text, JButton button, JPanel row) {
        JPanel newRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newRow.add(new JLabel(newLabel));
        newRow.add(text);
        newRow.add(button);
        row.add(newRow);
    }
}