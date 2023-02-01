

public abstract class LeaveRecord implements Comparable<LeaveRecord> {
	private Day f;
	private Day t;
	private int count;

	public abstract String getType();

	@Override
	public String toString() {
		return f.toString() + " to " + t.toString() + " [" + this.getType() + "]";
	}

	public LeaveRecord(Day from, Day to) {
		this.f = from;
		this.t = to;
		this.count = Day.countDays(from, to);
	}

	@Override
	public int compareTo(LeaveRecord another) {
		if (this.f.compareTo(another.getTo()) > 0) {
			return 1;
		} else if (this.t.compareTo(another.getFrom()) < 0){
			return -1;
		} else {
			return 0;
		}
	}

	public int getCount() {
		return this.count;
	}

	public Day getFrom() {
		return this.f;
	}

	public Day getTo() {
		return this.t;
	}
}