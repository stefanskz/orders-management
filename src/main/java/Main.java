import model.*;
import bll.*;
import start.ReflexiveSelection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StaffBLL staffBLL = new StaffBLL();
        Staff staff = new Staff(5, "Alex", "alex@gmail.com");
        //System.out.println(staffBLL.insertStaff(staff));
        //staffBLL.updateStaff(staff);
        //System.out.println(staffBLL.findStaffById(5));
        //staffBLL.deleteStaffById(5);
    }
}