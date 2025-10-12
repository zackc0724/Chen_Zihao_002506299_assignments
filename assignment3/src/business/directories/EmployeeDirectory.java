package business.directories;

import business.people.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDirectory {
    private final List<Employee> list = new ArrayList<>();
    public List<Employee> getList() { return list; }
    public Employee add(Employee e) { list.add(e); return e; }
}
