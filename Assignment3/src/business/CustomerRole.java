package business;

import javax.swing.JPanel;
import ui.CustomerPanel;

public class CustomerRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel mainContainer, UserAccount account, LibrarySystem system) {
        Customer customer = (Customer) account.getPerson();
        return new CustomerPanel(mainContainer, system, customer);
    }
}
