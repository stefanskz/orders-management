package presentation;

import model.*;
import bll.*;

import java.util.List;

public class Control {
    public static String[][] updateClientsTable() {
        List<Clients> allClients;
        ClientsBLL clientsBLL = new ClientsBLL();
        allClients = clientsBLL.findClients();

        if (allClients.isEmpty()) {
            return new String[][]{{"-", "-", "-"}};
        }

        String[][] newData = new String[allClients.size()][3];
        int k = 0;
        for (Clients index : allClients) {
            newData[k][0] = String.valueOf(index.getClientId());
            newData[k][1] = index.getName();
            newData[k][2] = index.getEmail();
            k++;
        }

        return newData;

    }

    public static void addInTable(String name, String email) {
        Clients clients = new Clients(0, name, email);
        ClientsBLL clientsBLL = new ClientsBLL();
        clientsBLL.insertClient(clients);
    }

    public static void addInProductsTable(String name, Double price, int quantity) {
        Products products = new Products(0, name, price, quantity);
        ProductsBLL productsBLL = new ProductsBLL();
        productsBLL.insertProduct(products);
    }

    public static void deleteFromTable(int id) {
        ClientsBLL clientsBLL = new ClientsBLL();
        clientsBLL.deleteClientById(id);
    }

    public static void deleteFromProductsTable(int id) {
        ProductsBLL productsBLL = new ProductsBLL();
        productsBLL.deleteProductById(id);
    }

    public static void updateInTable(int id, String name, String email) {
        Clients clients = new Clients(id, name, email);
        ClientsBLL clientsBLL = new ClientsBLL();
        clientsBLL.updateClient(clients);
    }

    public static void updateInTableProducts(int id, String name, Double price, int quantity) {
        Products products = new Products(id, name, price, quantity);
        ProductsBLL productsBLL = new ProductsBLL();
        productsBLL.updateProduct(products);
    }

    public static String[][] updateProductsTable() {
        List<Products> allProducts;
        ProductsBLL productsBLL = new ProductsBLL();
        allProducts = productsBLL.findProducts();
        if (allProducts.isEmpty()) {
            return new String[][]{{"-", "-", "-", "-"}};
        }
        String[][] newData = new String[allProducts.size()][4];
        int k = 0;
        for (Products index : allProducts) {
            newData[k][0] = String.valueOf(index.getProductId());
            newData[k][1] = index.getProductName();
            newData[k][2] = String.valueOf(index.getPrice());
            newData[k][3] = String.valueOf(index.getProductQuantity());
            k++;
        }
        return newData;
    }

}