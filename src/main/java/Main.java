import model.*;
import bll.*;

public class Main {
    public static void main(String[] args) {

        Products products = new Products(2, "Jucarie", 29.5);

        ProductsBLL productsBLL = new ProductsBLL();

        productsBLL.updateProduct(products);

    }
}