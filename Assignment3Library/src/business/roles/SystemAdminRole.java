package business.roles;

import business.EcoSystem;
import business.useraccount.UserAccount;
import javax.swing.JPanel;
import ui.admin.AdminWorkAreaPanel;

public class SystemAdminRole extends Role {
    @Override
    public JPanel createWorkArea(JPanel cardPanel, EcoSystem system, UserAccount account) {
        return new AdminWorkAreaPanel(cardPanel, system, account);
    }
}
