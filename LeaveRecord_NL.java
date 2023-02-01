

public class LeaveRecord_NL extends LeaveRecord {
	public LeaveRecord_NL(Day from, Day to) {
		super(from, to);
	}

	@Override
	public String getType() {
		return "NL";
	}

}