

public class Day implements Cloneable, Comparable<Day> {

	private int year;
	private int month;
	private int day;
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

	public void set(String sDay) // Set year,month,day based on a string like 01-Mar-2022
	{
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]); // Apply Integer.parseInt for sDayParts[0];
		this.year = Integer.parseInt(sDayParts[2]);
		this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
	}

	// Constructor
	public Day(int y, int m, int d) {
		this.year = y;
		this.month = m;
		this.day = d;
	}

	public Day(String sDay) {
		set(sDay);
	}

	public int getYear() {
		return this.year;
	}

	public int getMonth() {
		return this.month;
	}

	public int getDay() {
		return this.day;
	}

	public int getDayNum() {
		return Integer
				.parseInt(Integer.toString(this.year) + String.format("%02d", this.month) + String.format("%02d", this.day));
	}

	public static Day nextDay(Day current) {
		int y = current.getYear();
		int m = current.getMonth();
		int d = current.getDay();

		if (Day.valid(y, m, d + 1)) {
			return new Day(y, m, d + 1);
		} else if (Day.valid(y, m + 1, 1)) {
			return new Day(y, m + 1, 1);
		} else if (Day.valid(y + 1, 1, 1)) {
			return new Day(y + 1, 1, 1);
		}
		return null;
	}

	public static int countDays(Day from, Day to) {
		int count = 1;
		while (!from.toString().equals(to.toString())) {
			count++;
			from = Day.nextDay(from);
		}
		return count;
	}

	// check if a given year is a leap year
	static public boolean isLeapYear(int y) {
		if (y % 400 == 0)
			return true;
		else if (y % 100 == 0)
			return false;
		else if (y % 4 == 0)
			return true;
		else
			return false;
	}

	// check if y,m,d valid
	static public boolean valid(int y, int m, int d) {
		if (m < 1 || m > 12 || d < 1)
			return false;
		switch (m) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return d <= 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return d <= 30;
			case 2:
				if (isLeapYear(y))
					return d <= 29;
				else
					return d <= 28;
		}
		return false;
	}

	// Return a string for the day like dd MMM yyyy
	@Override
	public String toString() {
		return day + "-" + MonthNames.substring((month - 1) * 3, (month) * 3) + "-" + year; // (month-1)*3,(month)*3
	}

	@Override
	public int compareTo(Day another) {
		if (this.getDayNum() > another.getDayNum()) {
			return 1;
		} else if (this.getDayNum() < another.getDayNum()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public Day clone() {
		Day copy = null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}

}