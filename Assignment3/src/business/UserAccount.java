package business;

/**
 * User account for RBAC.
 */
public class UserAccount {

    private String username;
    private String password;
    private Object person; // Customer or Employee (or null for system admin)
    private Role role;

    public UserAccount(String username, String password, Object person, Role role) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public Object getPerson() {
        return person;
    }

    public Role getRole() {
        return role;
    }

    public boolean authenticate(String pwd) {
        return password != null && password.equals(pwd);
    }

    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}
