package business.directories;

import business.useraccount.UserAccount;
import java.util.ArrayList;
import java.util.List;

public class UserAccountDirectory {
    private final List<UserAccount> list = new ArrayList<>();
    public List<UserAccount> getList() { return list; }
    public UserAccount add(UserAccount ua) { list.add(ua); return ua; }

    public UserAccount find(String username, String password) {
        return list.stream().filter(ua -> ua.getUsername().equals(username) && ua.getPassword().equals(password))
            .findFirst().orElse(null);
    }
}
