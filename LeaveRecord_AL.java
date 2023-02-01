public class LeaveRecord_AL extends LeaveRecord {
	public LeaveRecord_AL(Day from, Day to) {
		super(from, to);
	}

	@Override
	public String getType() {
		return "AL";
	}

	public static boolean isValid(Day from, Day to, Employee e)
			throws ExLeaveMismatchException, ExOutOfAnnualLeavesException, ExInvalidAnnualLeavesException {
		int count = Day.countDays(from, to);
		if (count > 6) {
			throw new ExLeaveMismatchException((new LeaveRecord_AL(from, to)));
		}

		int amtLeft = e.getAnnualLeaves();
		for (LeaveRecord l : e.getLeaveRecords()) {
			if (l.getType().equals("AL") || l.getType().equals("BL")) {
				amtLeft -= l.getCount();
			}
		}

		if (!LeaveRecord_BL.hasTakenBlockLeave(e.getLeaveRecords())) {

			if (count > (amtLeft-7)) { //Block leave still not taken yet
				throw new ExInvalidAnnualLeavesException(amtLeft);
			}

		} else if (count > amtLeft) { //Has taken BL, days exceeds remaining
			throw new ExOutOfAnnualLeavesException(amtLeft);
		}
		
		return true;
	}

}