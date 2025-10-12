package ui.manager;

import business.EcoSystem;
import business.rental.RentalRecord;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewRentalRecordsPanel extends JPanel {
    private final EcoSystem system;
    private final UserAccount account;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Book","Customer","Rented At","Returned At","Status"},0);
    private final JTable table = new JTable(model);

    public ViewRentalRecordsPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.system = system;
        this.account = account;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        for (RentalRecord r : system.getRentalDirectory().getList()) {
            model.addRow(new Object[]{ r.getId(),
              r.getBook()!=null?r.getBook().getName():"",
              r.getCustomer()!=null?r.getCustomer().getName():"",
              r.getRentedAt(), r.getReturnedAt(), r.getStatus() });
        }
    }
}
