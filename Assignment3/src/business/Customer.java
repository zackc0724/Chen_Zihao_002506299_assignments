package business;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer that can rent books.
 */
public class Customer {

    private static int nextId = 1;

    private int customerId;
    private String name;
    private Branch branch;
    private List<RentalRecord> rentals = new ArrayList<>();

    public Customer(String name, Branch branch) {
        this.customerId = nextId++;
        this.name = name;
        this.branch = branch;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public Branch getBranch() {
        return branch;
    }

    public List<RentalRecord> getRentals() {
        return rentals;
    }

    public void addRental(RentalRecord record) {
        rentals.add(record);
    }

    @Override
    public String toString() {
        return name + " (ID " + customerId + ")";
    }
}
