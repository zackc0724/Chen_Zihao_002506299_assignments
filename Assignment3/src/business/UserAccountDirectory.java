package business;

import java.util.ArrayList;
import java.util.List;

/**
 * Directory of user accounts for the LibrarySystem.
 */
public class UserAccountDirectory {

    private List<UserAccount> accounts = new ArrayList<>();

    public List<UserAccount> getAccounts() {
        return accounts;
    }

    public UserAccount createUserAccount(String username, String password, Object person, Role role) {
        UserAccount ua = new UserAccount(username, password, person, role);
        accounts.add(ua);
        return ua;
    }

    public UserAccount findUserAccount(String username, String password) {
        for (UserAccount ua : accounts) {
            if (ua.getUsername().equals(username) && ua.authenticate(password)) {
                return ua;
            }
        }
        return null;
    }
}
