import java.util.ArrayList;

public class LeaveRecord_BL extends LeaveRecord {
	public LeaveRecord_BL(Day from, Day to) {
		super(from, to);
	}

	@Override
	public String getType() {
		return "BL";
	}

	public static boolean isValid(Day from, Day to, Employee e)
			throws ExLeaveMismatchException, ExOutOfAnnualLeavesException {
		int count = Day.countDays(from, to);
		if (count < 7) {
			throw new ExLeaveMismatchException((new LeaveRecord_BL(from, to)));
		}

		int amtLeft = e.getAnnualLeaves();
		for (LeaveRecord l : e.getLeaveRecords()) {
			if (l.getType().equals("AL")|| l.getType().equals("BL")) {
				amtLeft -= l.getCount();
			}
		}

		if (count > amtLeft) {
			throw new ExOutOfAnnualLeavesException(amtLeft);
		}
		return true;
	}

	public static boolean hasTakenBlockLeave(ArrayList<LeaveRecord> lset) {
		for (LeaveRecord l : lset) {
			if (l.getType().equals("BL")) {
				return true;
			}
		}
		return false;
	}

}