package bll;

import model.Bills;
import dao.BillsDAO;

import java.util.List;

/**
 * BillsBLL Class represents the bill logic, using BillsDAO
 */
public class BillsBLL {
    public int insertBills(Bills bills) {
        return BillsDAO.insert(bills);
    }

    public List<Bills> findAllClientBills(int id) {
        return BillsDAO.find(id);
    }
}