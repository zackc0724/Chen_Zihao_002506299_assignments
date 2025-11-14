package business;

import java.util.ArrayList;
import java.util.List;

/**
 * A branch has a name and a single Library.
 */
public class Branch {

    private static int nextId = 1;

    private int id;
    private String name;
    private Library library;
    private Employee branchManager;

    public Branch(String name) {
        this.id = nextId++;
        this.name = name;
        this.library = new Library("B-" + id, this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Library getLibrary() {
        return library;
    }

    public Employee getBranchManager() {
        return branchManager;
    }

    public void setBranchManager(Employee branchManager) {
        this.branchManager = branchManager;
        if (branchManager != null) {
            branchManager.setBranch(this);
        }
    }

    @Override
    public String toString() {
        return name + " (#" + id + ")";
    }
}
