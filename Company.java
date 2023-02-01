

import java.util.ArrayList;
import java.util.Collections;

public class Company {
	private ArrayList<Employee> allEmployees;
	private ArrayList<Team> allTeams;

	private static Company instance = new Company();

	private Company() {
		allEmployees = new ArrayList<Employee>();
		allTeams = new ArrayList<Team>();
	}

	public static Company getInstance() {
		return instance;
	}

	public Team getTeam(String query) {
		return Team.searchTeam(allTeams, query);
	}

	public Employee getEmployee(String query) {
		return Employee.searchEmployee(allEmployees, query);
	}

	public void listTeams() {
		Team.list(allTeams);
	}

	public Employee createEmployee(String s, int n) throws ExDuplicateException, ExOutOfRangeException {
		if (Employee.searchEmployee(allEmployees, s) != null) {
			throw new ExDuplicateException(new Employee());
		}
		Employee e = new Employee(s, n);
		return e;
	}

	public LeaveRecord takeLeave(Employee e, String leaveType, String fromDate, String toDate)
			throws ExInvalidDateException, ExLeaveOverlapException,
			ExLeaveMismatchException, ExOutOfAnnualLeavesException, ExInvalidAnnualLeavesException,
			ExOutOfSickLeavesException {
		Day from = new Day(fromDate);
		if (from.compareTo(SystemDate.getInstance()) == -1) {
			throw new ExInvalidDateException(SystemDate.getInstance());
		}
		Day to = new Day(toDate);
		return e.takeLeave(leaveType, from, to);
	}

	public void listEmployees() {
		String result = "";
		for (Employee e : allEmployees) {
			result += e.toString() + "\n";
		}
		System.out.println(result.trim());
	}

	public void addEmployee(Employee e) {
		allEmployees.add(e);
		Collections.sort(allEmployees); // allEmployees
	}

	public void removeEmployee(Employee e) {
		allEmployees.remove(e);
	}

	public Team createTeam(String tname, String lead) throws ExEmployeeNotFoundException, ExDuplicateException {
		Employee e = Employee.searchEmployee(allEmployees, lead);
		if (e == null) {
			throw new ExEmployeeNotFoundException();
		}
		if (Team.searchTeam(allTeams, tname) != null) {
			throw new ExDuplicateException(new Team());
		}
		Team t = new Team(tname, e);
		return t;
	}

	public void addTeamMember(String employeeName, String teamName)
			throws ExEmployeeNotFoundException, ExTeamNotFoundException, ExEmployeeDuplicateAssignmentException {
		Employee e = Employee.searchEmployee(allEmployees, employeeName);
		if (e == null) {
			throw new ExEmployeeNotFoundException();
		}
		Team t = Team.searchTeam(allTeams, teamName);
		if (t == null) {
			throw new ExTeamNotFoundException();
		}
		if (t.isTeamMember(e)) {
			throw new ExEmployeeDuplicateAssignmentException();
		}
		t.add(e);
	}

	public void listLeaves() {
		for (Employee e : allEmployees) {
			System.out.println(e.getName() + ":");
			if (e.getLeaveRecords().size() == 0) {
				System.out.println("No leave record");
			} else {
				for (LeaveRecord l : e.getLeaveRecords()) {
					System.out.println(l);
				}
			}

		}

	}

	public void listLeaves(String employeeName) {
		try {
			Employee e = Employee.searchEmployee(allEmployees, employeeName);
			if (e == null) {
				throw new ExEmployeeNotFoundException();
			}
			if (e.getLeaveRecords().size() == 0) {
				System.out.println("No leave record");
			} else {
				for (LeaveRecord l : e.getLeaveRecords()) {
					System.out.println(l);
				}
			}
		} catch (ExEmployeeNotFoundException e) {
			System.out.println(e);
		}
	}

	public void listRoles(String employeeName) {
		try {
			Employee e = Employee.searchEmployee(allEmployees, employeeName);
			if (e == null) {
				throw new ExEmployeeNotFoundException();
			}
			boolean hasRole = false;
			for (Team t : allTeams) {
				if (t.isTeamLeader(e)) {
					hasRole = true;
					System.out.println(t.getTeamName() + " (Head of Team)");
				} else if (t.isTeamMember(e)) {
					System.out.println(t.getTeamName());
					hasRole = true;
				}
			}
			if (hasRole == false) {
				throw new ExEmployeeNotAssignedException();
			}
		} catch (ExEmployeeNotFoundException e) {
			System.out.println(e);
		} catch (ExEmployeeNotAssignedException e) {
			System.out.println(e);
		}
	}

	public void listTeamMembers() {
		for (Team t : allTeams) {
			System.out.println(t.getTeamName() + ":");
			for (Employee e : t.getTeamMembers()) {
				System.out.println((t.getRole(e)));
			}
			System.out.println();
		}
	}

	public void removeTeamMember(String employeeName,
			String teamName) {
		Employee e = Employee.searchEmployee(allEmployees, employeeName);
		Team t = Team.searchTeam(allTeams, teamName);
		t.remove(e);
	}

	public void addTeam(Team t) {
		allTeams.add(t);
		Collections.sort(allTeams);
	}

	public void removeTeam(Team t) {
		allTeams.remove(t);
	}
}
