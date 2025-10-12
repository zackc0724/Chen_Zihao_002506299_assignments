package business.directories;

import business.people.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerDirectory {
    private final List<Customer> list = new ArrayList<>();
    public List<Customer> getList() { return list; }
    public Customer add(Customer c) { list.add(c); return c; }
}
