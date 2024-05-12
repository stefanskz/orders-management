import model.*;
import bll.*;

public class Main {
    public static void main(String[] args) {
        Bills bills = new Bills(14, 20, 30, 40, 50.0);
        BillsBLL billsBLL = new BillsBLL();
        System.out.println(billsBLL.findAllClientBills(20));
    }
}