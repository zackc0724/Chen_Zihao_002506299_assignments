package business;

import javax.swing.JPanel;
import ui.BranchManagerPanel;

public class BranchManagerRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel mainContainer, UserAccount account, LibrarySystem system) {
        Employee emp = (Employee) account.getPerson();
        return new BranchManagerPanel(mainContainer, system, emp);
    }
}
