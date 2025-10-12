package business.people;

public class Customer {
    private static int COUNTER = 1;
    private final int customerId;
    private String name;

    public Customer(String name) {
        this.customerId = COUNTER++;
        this.name = name;
    }
    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @Override public String toString() { return name + " (C" + customerId + ")"; }
}
