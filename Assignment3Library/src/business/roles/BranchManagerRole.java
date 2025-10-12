package business.roles;

import business.EcoSystem;
import business.useraccount.UserAccount;
import javax.swing.JPanel;
import ui.manager.ManagerWorkAreaPanel;

public class BranchManagerRole extends Role {
    @Override
    public JPanel createWorkArea(JPanel cardPanel, EcoSystem system, UserAccount account) {
        return new ManagerWorkAreaPanel(cardPanel, system, account);
    }
}
