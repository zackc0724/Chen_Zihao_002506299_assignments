package ui.admin;

import business.EcoSystem;
import business.useraccount.UserAccount;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import ui.MainJFrame;

public class AdminWorkAreaPanel extends JPanel {
    private final JPanel cardPanel;
    private final EcoSystem system;
    private final UserAccount account;

    public AdminWorkAreaPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.cardPanel = cardPanel;
        this.system = system;
        this.account = account;

        setLayout(new GridLayout(2,2,16,16));
        JButton btnBranches = new JButton("Manage Branches");
        JButton btnEmployees = new JButton("Manage Employees (Managers)");
        JButton btnCustomers = new JButton("Manage Customers");
        JButton btnUsers = new JButton("Manage User Accounts");
        add(btnBranches); add(btnEmployees); add(btnCustomers); add(btnUsers);

        btnBranches.addActionListener(e -> MainJFrame.insertTo(cardPanel, new ManageBranchesPanel(cardPanel, system)));
        btnEmployees.addActionListener(e -> MainJFrame.insertTo(cardPanel, new ManageEmployeesPanel(cardPanel, system)));
        btnCustomers.addActionListener(e -> MainJFrame.insertTo(cardPanel, new ManageCustomersPanel(cardPanel, system)));
        btnUsers.addActionListener(e -> MainJFrame.insertTo(cardPanel, new ManageUserAccountsPanel(cardPanel, system)));
    }
}
