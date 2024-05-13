package model;

public class Staff {
    private int id;
    private String staffName;
    private String staffEmail;

    public Staff(int staffId, String staffName, String staffEmail) {
        this.id = staffId;
        this.staffName = staffName;
        this.staffEmail = staffEmail;
    }

    public Staff() {

    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + id +
                ", staffName='" + staffName + '\'' +
                ", staffEmail='" + staffEmail + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int staffId) {
        this.id = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }
}