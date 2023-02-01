import java.util.*;

public class Team implements Comparable<Team> {
    private String teamName;
    private Employee head;
    private Day dateSetup;
    private ArrayList<Employee> teamMembers;

    public Team() {
    }

    public Team(String n, Employee hd) {
        this.teamName = n;
        this.head = hd;
        this.teamMembers = new ArrayList<>();
        this.teamMembers.add(hd);
        this.dateSetup = SystemDate.getInstance().clone();
    }

    public String getTeamName() {
        return this.teamName;
    }

    public Employee getHead() {
        return this.head;
    }

    public Day getDay() {
        return this.dateSetup;
    }

    public ArrayList<Employee> getTeamMembers() {
        return this.teamMembers;
    }

    public static void list(ArrayList<Team> list) {
        // Learn: "-" means left-aligned
        System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
        for (Team t : list)
            System.out.printf("%-30s%-10s%-13s\n", t.getTeamName(), t.getHead().getName(), t.getDay());
    }

    public static Team searchTeam(ArrayList<Team> list, String teamName) {
        for (Team t : list) {
            if (t.getTeamName().equals(teamName))
                return t;
        }
        return null;
    }

    public boolean isTeamMember(Employee e) {
        return this.teamMembers.contains(e);
    }

    public boolean isTeamLeader(Employee e) {
        return (e == this.head);
    }

    public String getRole(Employee e) {
        if (isTeamLeader(e)) {
            return e.getName() + " (Head of Team)";
        } else if (isTeamMember(e)) {
            return e.getName();
        } else {
            return null;
        }
    }

    public void add(Employee e) {
        this.teamMembers.add(e);
        Collections.sort(this.teamMembers);
    }

    public void remove(Employee e) {
        this.teamMembers.remove(e);
    }

    @Override
    public int compareTo(Team another) {
        return this.teamName.compareTo(another.teamName);
    }
}