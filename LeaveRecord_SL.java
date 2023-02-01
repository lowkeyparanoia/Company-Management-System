

import java.util.ArrayList;

public class LeaveRecord_SL extends LeaveRecord {
	public LeaveRecord_SL(Day from, Day to) {
		super(from, to);
	}

	@Override
	public String getType() {
		return "SL";
	}

	public static boolean isValid(Day from, Day to, Employee e) throws ExLeaveMismatchException, ExOutOfSickLeavesException {
		int count = Day.countDays(from, to);
		int amtLeft = 135;
		for (LeaveRecord l : e.getLeaveRecords()) {
			if (l.getType().equals("SL")) {
				amtLeft -= l.getCount();
			}
		}
		if (count > amtLeft) {
			throw new ExOutOfSickLeavesException(amtLeft);
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