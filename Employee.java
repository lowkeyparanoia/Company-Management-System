import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee> {
    private String name;
    private int annualLeaves;
    private ArrayList<LeaveRecord> leaveSets = new ArrayList<>();

    public Employee() {
    }

    public void addToLeaveSet(LeaveRecord l) {
        leaveSets.add(l);
        Collections.sort(leaveSets);
    }

    public void removeFromLeaveSet(LeaveRecord l) {
        leaveSets.remove(l);
    }

    public LeaveRecord takeLeave(String leaveType, Day from, Day to) throws ExLeaveOverlapException,
            ExLeaveMismatchException, ExOutOfAnnualLeavesException, ExInvalidAnnualLeavesException,
            ExOutOfSickLeavesException {
        for (LeaveRecord l : leaveSets) { // Check for overlapping leaves
            // if !(from and to are before the leave's from, OR, if from and to are after
            // the leave's to), OR, if to is before from

            if (to.compareTo(l.getFrom()) < 0) {
                if (from.compareTo(l.getFrom()) < 0) {
                    // all good
                } else {
                    throw new ExLeaveOverlapException(l);
                }
            } else if (from.compareTo(l.getTo()) > 0) {
                if (to.compareTo(l.getTo()) > 0) {
                    // all good
                } else {
                    throw new ExLeaveOverlapException(l);
                }
            } else {
                throw new ExLeaveOverlapException(l);

            }
            // to.compareTo(from) < 0)
        }

        switch (leaveType) {
            case "AL":
                if (LeaveRecord_AL.isValid(from, to, this)) {
                    LeaveRecord_AL newLeave = new LeaveRecord_AL(from, to);
                    addToLeaveSet(newLeave);
                    return newLeave;
                }
                break;
            case "BL":
                if (LeaveRecord_BL.isValid(from, to, this)) {
                    LeaveRecord_BL newLeave = new LeaveRecord_BL(from, to);
                    addToLeaveSet(newLeave);
                    return newLeave;
                }
                break;
            case "SL":
                if (LeaveRecord_SL.isValid(from, to, this)) {
                    LeaveRecord_SL newLeave = new LeaveRecord_SL(from, to);
                    addToLeaveSet(newLeave);
                    return newLeave;
                }
                break;
            case "NL":
                LeaveRecord_NL newLeave = new LeaveRecord_NL(from, to);
                addToLeaveSet(newLeave);
                return newLeave;
        }

        return null;
    }

    public Employee(String n, int yle) throws ExOutOfRangeException {
        this.name = n;
        if (yle >= 0 && yle <= 300)
            this.annualLeaves = yle;
        else
            throw new ExOutOfRangeException();
    }

    public String getName() {
        return this.name;
    }

    public int getAnnualLeaves() {
        return this.annualLeaves;
    }

    public ArrayList<LeaveRecord> getLeaveRecords() {
        return this.leaveSets;
    }

    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) {
        for (Employee e : list) {
            if (e.getName().equals(nameToSearch)) {
                return e;
            }
        }
        return null;
    }

    public String toString() {
        return this.name + " (Entitled Annual Leaves: " + this.annualLeaves + " days)";
    }

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }
}
