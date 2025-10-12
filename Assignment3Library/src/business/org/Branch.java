package business.org;

import business.people.Employee;

public class Branch {
    private String name;
    private Library library;
    private Employee branchManager; // one manager per branch

    public Branch(String name, int libraryBuildingNo) {
        this.name = name;
        this.library = new Library(libraryBuildingNo);
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Library getLibrary() { return library; }
    public Employee getBranchManager() { return branchManager; }
    public void setBranchManager(Employee m) { this.branchManager = m; }

    @Override public String toString() { return name + " (" + library + ")"; }
}
