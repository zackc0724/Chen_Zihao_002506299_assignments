package ui.manager;

import business.EcoSystem;
import business.useraccount.UserAccount;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import ui.MainJFrame;

public class ManagerWorkAreaPanel extends JPanel {
    private final JPanel cardPanel;
    private final EcoSystem system;
    private final UserAccount account;

    public ManagerWorkAreaPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.cardPanel = cardPanel;
        this.system = system;
        this.account = account;

        setLayout(new GridLayout(1,3,16,16));
        JButton btnAuthors = new JButton("Manage Authors");
        JButton btnBooks = new JButton("Manage Books");
        JButton btnRentals = new JButton("View Rentals");
        add(btnAuthors); add(btnBooks); add(btnRentals);

        btnAuthors.addActionListener(e -> MainJFrame.insertTo(cardPanel, new ManageAuthorsPanel(cardPanel, system, account)));
        btnBooks.addActionListener(e -> MainJFrame.insertTo(cardPanel, new ManageBooksPanel(cardPanel, system, account)));
        btnRentals.addActionListener(e -> MainJFrame.insertTo(cardPanel, new ViewRentalRecordsPanel(cardPanel, system, account)));
    }
}
