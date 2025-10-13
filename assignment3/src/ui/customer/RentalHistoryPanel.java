package ui.customer;

import business.EcoSystem;
import business.rental.RentalRecord;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.common.UI;

public class RentalHistoryPanel extends JPanel {
    private final EcoSystem system;
    private final UserAccount account;
    private final JPanel cardPanel;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Book","Rented At","Returned At","Status"},0){
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);

    public RentalHistoryPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.system = system;
        this.account = account;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        refresh();
        UI.addLogoutBar(this, cardPanel, system);
    }

    private void refresh() {
        model.setRowCount(0);
        for (RentalRecord r : system.getRentalDirectory().forCustomer(account.getCustomer().getCustomerId())) {
            model.addRow(new Object[]{ r.getId(),
              r.getBook()!=null?r.getBook().getName():"",
              r.getRentedAt(), r.getReturnedAt(), r.getStatus() });
        }
    }
}
