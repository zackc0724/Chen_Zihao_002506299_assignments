package business;

import javax.swing.JPanel;
import ui.SystemAdminPanel;

public class SystemAdminRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel mainContainer, UserAccount account, LibrarySystem system) {
        return new SystemAdminPanel(mainContainer, system);
    }
}
