package business;

import business.directories.*;

public class EcoSystem {
    private final BranchDirectory branchDirectory = new BranchDirectory();
    private final AuthorDirectory authorDirectory = new AuthorDirectory();
    private final BookDirectory bookDirectory = new BookDirectory();
    private final CustomerDirectory customerDirectory = new CustomerDirectory();
    private final EmployeeDirectory employeeDirectory = new EmployeeDirectory();
    private final RentalDirectory rentalDirectory = new RentalDirectory();
    private final UserAccountDirectory userAccountDirectory = new UserAccountDirectory();

    public BranchDirectory getBranchDirectory() { return branchDirectory; }
    public AuthorDirectory getAuthorDirectory() { return authorDirectory; }
    public BookDirectory getBookDirectory() { return bookDirectory; }
    public CustomerDirectory getCustomerDirectory() { return customerDirectory; }
    public EmployeeDirectory getEmployeeDirectory() { return employeeDirectory; }
    public RentalDirectory getRentalDirectory() { return rentalDirectory; }
    public UserAccountDirectory getUserAccountDirectory() { return userAccountDirectory; }
}
