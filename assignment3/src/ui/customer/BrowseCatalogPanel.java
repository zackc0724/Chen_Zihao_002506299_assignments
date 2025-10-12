package ui.customer;

import business.EcoSystem;
import business.items.Book;
import business.rental.RentalRecord;
import business.rental.RentalStatus;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.common.UI;

public class BrowseCatalogPanel extends JPanel {
    private final EcoSystem system;
    private final UserAccount account;
    private final JPanel cardPanel;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"SN","Name","Author","Branch","Available"},0) {
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JButton btnRent = new JButton("Rent Selected (if Available)");

    public BrowseCatalogPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.system = system;
        this.account = account;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnRent, BorderLayout.NORTH);

        btnRent.addActionListener(e -> rentSelected());
        refresh();
        UI.addLogoutBar(this, cardPanel, system);
    }

    private void refresh() {
        model.setRowCount(0);
        for (Book b : system.getBookDirectory().getList()) {
            model.addRow(new Object[]{ b.getSerialNumber(), b.getName(),
                b.getAuthor()!=null?b.getAuthor().getName():"",
                b.getOwnerBranch()!=null?b.getOwnerBranch().getName():"",
                b.isAvailable()? "Available":"Rented" });
        }
    }

    private void rentSelected() {
        int row = table.getSelectedRow();
        if(row<0) { JOptionPane.showMessageDialog(this, "Select a book"); return; }
        Book b = system.getBookDirectory().getList().get(row);
        if(!b.isAvailable()) { JOptionPane.showMessageDialog(this, "Not available"); return; }
        b.markRented();
        RentalRecord r = new RentalRecord();
        r.setBook(b);
        r.setCustomer(account.getCustomer());
        r.setLibrary(b.getOwnerBranch().getLibrary());
        r.setRentedAt(LocalDateTime.now());
        r.setStatus(RentalStatus.RENTED);
        system.getRentalDirectory().add(r);
        refresh();
    }
}
