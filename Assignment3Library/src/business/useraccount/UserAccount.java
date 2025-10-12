package business.useraccount;

import business.people.Customer;
import business.people.Employee;
import business.roles.Role;

public class UserAccount {
    private String username;
    private String password;
    private Role role;
    private Customer customer; // optional
    private Employee employee; // optional

    public UserAccount(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    @Override public String toString() { return username + " (" + role + ")"; }
}
