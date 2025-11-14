package business;

/**
 * Employee representing Branch Manager.
 */
public class Employee {

    private static int nextId = 1;

    private int employeeId;
    private String name;
    private int experience;
    private Branch branch;

    public Employee(String name, int experience, Branch branch) {
        this.employeeId = nextId++;
        this.name = name;
        this.experience = experience;
        this.branch = branch;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch b) {
        this.branch = b;
    }

    @Override
    public String toString() {
        return name + " (ID " + employeeId + ")";
    }
}
