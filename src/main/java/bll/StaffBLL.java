package bll;

import dao.AbstractDAO;
import model.Staff;

public class StaffBLL extends AbstractDAO<Staff> {
    private AbstractDAO<Staff> staffDAO;

    public StaffBLL() {
        super(Staff.class);
        staffDAO = new AbstractDAO<>(Staff.class);
    }

    public Staff findStaffById(int id) {
        return staffDAO.findById(id);
    }

}