package business.people;

public class Employee {
    private static int COUNTER = 1;
    private final int employeeId;
    private String name;
    private int experienceYears;

    public Employee(String name, int experienceYears) {
        this.employeeId = COUNTER++;
        this.name = name;
        this.experienceYears = experienceYears;
    }
    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int y) { this.experienceYears = y; }

    @Override public String toString() { return name + " (ID " + employeeId + ")"; }
}
