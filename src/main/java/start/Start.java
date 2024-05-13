package start;

import model.Clients;
import model.Products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Start {
    public static void main(String[] args) {
        Clients clients = new Clients(2, "Ana", "ana@gmail.com");
        Clients clients1 = new Clients(3, "Alex", "alex@gmail.com");
        Products products = new Products(2, "Car", 3.0, 5);
        List<Object> list = new ArrayList<>();
        list.add(clients);
        list.add(clients1);
        String[][] data;
        data = ReflexiveSelection.retrieveProp(list);
        System.out.println(Arrays.deepToString(data));
    }
}