package ui.customer;

import business.EcoSystem;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import ui.MainJFrame;
import ui.common.UI;

public class CustomerWorkAreaPanel extends JPanel {
    private final JPanel cardPanel;
    private final EcoSystem system;
    private final UserAccount account;

    public CustomerWorkAreaPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.cardPanel = cardPanel;
        this.system = system;
        this.account = account;

        setLayout(new BorderLayout(8,8));

        JPanel center = new JPanel(new GridLayout(1,3,16,16));
        JButton btnBrowse = new JButton("Browse & Rent");
        JButton btnReturn = new JButton("Return Book");
        JButton btnHistory = new JButton("Rental History");
        center.add(btnBrowse); center.add(btnReturn); center.add(btnHistory);
        add(center, BorderLayout.CENTER);

        btnBrowse.addActionListener(e -> MainJFrame.insertTo(cardPanel, new BrowseCatalogPanel(cardPanel, system, account)));
        btnReturn.addActionListener(e -> MainJFrame.insertTo(cardPanel, new ReturnBookPanel(cardPanel, system, account)));
        btnHistory.addActionListener(e -> MainJFrame.insertTo(cardPanel, new RentalHistoryPanel(cardPanel, system, account)));

        UI.addLogoutBar(this, cardPanel, system);
    }
}
