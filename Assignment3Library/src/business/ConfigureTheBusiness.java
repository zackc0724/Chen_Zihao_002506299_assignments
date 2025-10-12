package business;

import business.directories.*;
import business.items.Author;
import business.items.Book;
import business.org.Branch;
import business.people.Customer;
import business.people.Employee;
import business.roles.*;
import business.useraccount.UserAccount;
import java.time.LocalDate;

public final class ConfigureTheBusiness {
    private ConfigureTheBusiness() {}

    public static EcoSystem configure() {
        EcoSystem system = new EcoSystem();
        BranchDirectory bd = system.getBranchDirectory();
        CustomerDirectory cd = system.getCustomerDirectory();
        EmployeeDirectory ed = system.getEmployeeDirectory();
        AuthorDirectory ad = system.getAuthorDirectory();
        BookDirectory books = system.getBookDirectory();
        UserAccountDirectory uad = system.getUserAccountDirectory();

        // Branches + managers
        Branch b1 = bd.add(new Branch("Downtown", 101));
        Branch b2 = bd.add(new Branch("Uptown", 202));
        Employee m1 = ed.add(new Employee("Alice Manager", 7));
        Employee m2 = ed.add(new Employee("Bob Manager", 5));
        b1.setBranchManager(m1);
        b2.setBranchManager(m2);

        // Authors
        Author a1 = ad.add(new Author("Octavia Butler"));
        Author a2 = ad.add(new Author("Haruki Murakami"));
        Author a3 = ad.add(new Author("Liu Cixin"));

        // Books (assign to branches)
        books.add(new Book("Kindred", LocalDate.now().minusYears(1), 320, "English", a1, b1));
        books.add(new Book("Norwegian Wood", LocalDate.now().minusYears(2), 296, "English", a2, b1));
        books.add(new Book("The Three-Body Problem", LocalDate.now().minusYears(3), 416, "English", a3, b2));

        // Customers (>=5)
        Customer c1 = cd.add(new Customer("Zihao Chen"));
        Customer c2 = cd.add(new Customer("Ana Silva"));
        Customer c3 = cd.add(new Customer("Sam Patel"));
        Customer c4 = cd.add(new Customer("Maria Garcia"));
        Customer c5 = cd.add(new Customer("Ethan Wang"));

        // Accounts: 1 sysadmin, 2 managers, 5 customers
        uad.add(new UserAccount("admin", "admin", new SystemAdminRole()));
        UserAccount mu1 = uad.add(new UserAccount("mgr1", "mgr1", new BranchManagerRole()));
        mu1.setEmployee(m1);
        UserAccount mu2 = uad.add(new UserAccount("mgr2", "mgr2", new BranchManagerRole()));
        mu2.setEmployee(m2);

        UserAccount cu1 = uad.add(new UserAccount("c1", "c1", new CustomerRole())); cu1.setCustomer(c1);
        UserAccount cu2 = uad.add(new UserAccount("c2", "c2", new CustomerRole())); cu2.setCustomer(c2);
        UserAccount cu3 = uad.add(new UserAccount("c3", "c3", new CustomerRole())); cu3.setCustomer(c3);
        UserAccount cu4 = uad.add(new UserAccount("c4", "c4", new CustomerRole())); cu4.setCustomer(c4);
        UserAccount cu5 = uad.add(new UserAccount("c5", "c5", new CustomerRole())); cu5.setCustomer(c5);

        return system;
    }
}
