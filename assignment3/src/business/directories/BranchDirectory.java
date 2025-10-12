package business.directories;

import business.org.Branch;
import java.util.ArrayList;
import java.util.List;

public class BranchDirectory {
    private final List<Branch> list = new ArrayList<>();
    public List<Branch> getList() { return list; }
    public Branch add(Branch b) { list.add(b); return b; }
    public void remove(Branch b) { list.remove(b); }
}
