import bll.ClientsBLL;
import model.Clients;

public class Main {
    public static void main(String[] args) {

        Clients clients = new Clients(3, "David", "david@gmail.com");

        ClientsBLL clientsBLL = new ClientsBLL();

        clientsBLL.updateClient(clients);

    }
}