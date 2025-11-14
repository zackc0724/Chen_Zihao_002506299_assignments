package business;

import java.util.ArrayList;
import java.util.List;

/**
 * Root model object for the Library Management System.
 * Uses ConfigureTheBusiness-style static factory to create sample data.
 */
public class LibrarySystem {

    private List<Branch> branches = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private List<RentalRecord> rentalRecords = new ArrayList<>();
    private UserAccountDirectory userAccountDirectory = new UserAccountDirectory();

    public List<Branch> getBranches() {
        return branches;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<RentalRecord> getRentalRecords() {
        return rentalRecords;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    public void addBranch(Branch b) {
        branches.add(b);
    }

    public void removeBranch(Branch b) {
        branches.remove(b);
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public void addAuthor(Author a) {
        authors.add(a);
    }

    public void addRentalRecord(RentalRecord r) {
        rentalRecords.add(r);
    }

    /**
     * ConfigureTheBusiness-style configuration: pre-populate basic demo data.
     */
    public static LibrarySystem configure() {
        LibrarySystem system = new LibrarySystem();

        // create branches and libraries
        Branch boston = new Branch("Boston Public");
        Branch seaport = new Branch("Seaport Library");
        system.addBranch(boston);
        system.addBranch(seaport);

        // create branch managers (employees)
        Employee bm1 = new Employee("Alice Manager", 5, boston);
        Employee bm2 = new Employee("Bob Manager", 7, seaport);
        system.addEmployee(bm1);
        system.addEmployee(bm2);
        boston.setBranchManager(bm1);
        seaport.setBranchManager(bm2);

        // create authors
        Author a1 = new Author("Emily Brontë");
        Author a2 = new Author("George Orwell");
        Author a3 = new Author("Haruki Murakami");
        system.addAuthor(a1);
        system.addAuthor(a2);
        system.addAuthor(a3);

        // create books in libraries
        Library libBoston = boston.getLibrary();
        Library libSeaport = seaport.getLibrary();

        libBoston.addBook(new Book("Wuthering Heights", a1, 320, "English"));
        libBoston.addBook(new Book("1984", a2, 270, "English"));
        libSeaport.addBook(new Book("Kafka on the Shore", a3, 480, "Japanese"));
        libSeaport.addBook(new Book("Norwegian Wood", a3, 390, "Japanese"));

        // system admin
        UserAccount admin = system.getUserAccountDirectory().createUserAccount(
                "admin", "admin", null, new SystemAdminRole());
        // 5 customers
        Customer c1 = new Customer("Charlie Customer", boston);
        Customer c2 = new Customer("Diana Reader", boston);
        Customer c3 = new Customer("Eve Student", seaport);
        Customer c4 = new Customer("Frank User", seaport);
        Customer c5 = new Customer("Grace Member", seaport);

        system.addCustomer(c1);
        system.addCustomer(c2);
        system.addCustomer(c3);
        system.addCustomer(c4);
        system.addCustomer(c5);

        // create customer accounts
        system.getUserAccountDirectory().createUserAccount("c1", "c1", c1, new CustomerRole());
        system.getUserAccountDirectory().createUserAccount("c2", "c2", c2, new CustomerRole());
        system.getUserAccountDirectory().createUserAccount("c3", "c3", c3, new CustomerRole());
        system.getUserAccountDirectory().createUserAccount("c4", "c4", c4, new CustomerRole());
        system.getUserAccountDirectory().createUserAccount("c5", "c5", c5, new CustomerRole());

        // branch manager user accounts
        system.getUserAccountDirectory().createUserAccount("bm1", "bm1", bm1, new BranchManagerRole());
        system.getUserAccountDirectory().createUserAccount("bm2", "bm2", bm2, new BranchManagerRole());

        return system;
    }
}
