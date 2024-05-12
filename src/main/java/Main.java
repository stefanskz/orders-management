import model.*;
import bll.*;

public class Main {
    public static void main(String[] args) {
        ProductsBLL productsBLL = new ProductsBLL();
        System.out.println(productsBLL.findProducts());
    }
}