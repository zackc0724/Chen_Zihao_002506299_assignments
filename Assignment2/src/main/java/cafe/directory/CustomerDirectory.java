package cafe.directory;

import com.cafe.model.Customer;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerDirectory {
    private final List<Customer> customers = new ArrayList<>();

    public void add(Customer c) { customers.add(c); }
    public void remove(Customer c) { customers.remove(c); }
    public List<Customer> getAll() { return Collections.unmodifiableList(customers); }

    public Customer findById(int id) {
        for (Customer c : customers) if (c.getId() == id) return c;
        return null;
    }

    public List<Customer> findByName(String name) {
        String n = name.trim().toLowerCase();
        return customers.stream().filter(c -> (c.getFirstName() + " " + c.getLastName()).toLowerCase().contains(n)).collect(Collectors.toList());
    }
}
