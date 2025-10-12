package business.roles;

import business.EcoSystem;
import business.useraccount.UserAccount;
import javax.swing.JPanel;

public abstract class Role {
    public abstract JPanel createWorkArea(JPanel cardPanel, EcoSystem system, UserAccount account);
    @Override public String toString() { return this.getClass().getSimpleName(); }
}
