package bll;

import dao.OrdersDAO;
import model.Orders;

import java.util.List;
/**
 * OrdersBLL Class represents the order logic, using OrdersDAO
 */
public class OrdersBLL {
    public int insertOrders(Orders orders) {
        return OrdersDAO.insert(orders);
    }

    public List<Orders> findOrders() {
        return OrdersDAO.find();
    }
}