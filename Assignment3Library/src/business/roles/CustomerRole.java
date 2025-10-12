package business.roles;

import business.EcoSystem;
import business.useraccount.UserAccount;
import javax.swing.JPanel;
import ui.customer.CustomerWorkAreaPanel;

public class CustomerRole extends Role {
    @Override
    public JPanel createWorkArea(JPanel cardPanel, EcoSystem system, UserAccount account) {
        return new CustomerWorkAreaPanel(cardPanel, system, account);
    }
}
