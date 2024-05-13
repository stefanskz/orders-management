import model.*;
import bll.*;
import start.ReflexiveSelection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StaffBLL staffBLL = new StaffBLL();
        Staff staff = staffBLL.findStaffById(2);
        System.out.println(staff);
    }
}