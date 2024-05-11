import model.*;
import bll.*;

public class Main {
    public static void main(String[] args) {

        OrdersBLL ordersBLL = new OrdersBLL();

        System.out.println(ordersBLL.findOrders());

    }
}