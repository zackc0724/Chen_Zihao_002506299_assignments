package ui.manager;

import business.EcoSystem;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import ui.common.UI;

public class ManagerWorkAreaPanel extends JPanel {
    private final JPanel cardPanel;
    private final EcoSystem system;
    private final UserAccount account;

    public ManagerWorkAreaPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.cardPanel = cardPanel;
        this.system = system;
        this.account = account;

        setLayout(new BorderLayout(8, 8));
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.addTab("Books",   new ManageBooksPanel(cardPanel, system, account));
        tabs.addTab("Authors", new ManageAuthorsPanel(cardPanel, system, account));
        tabs.addTab("Rentals", new ViewRentalRecordsPanel(cardPanel, system, account));
        add(tabs, BorderLayout.CENTER);
        UI.addLogoutBar(this, cardPanel, system);
    }
}
