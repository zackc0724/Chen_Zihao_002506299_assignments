package ui.manager;

import business.EcoSystem;
import business.org.Branch;
import business.rental.RentalRecord;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import java.util.Objects;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.common.UI;

public class ViewRentalRecordsPanel extends JPanel {
    private final EcoSystem system;
    private final UserAccount account;
    private final JPanel cardPanel;
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID","Book","Customer","Rented At","Returned At","Status"},0){
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);

    public ViewRentalRecordsPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.system = system;
        this.account = account;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout(6,6));
        add(new JScrollPane(table), BorderLayout.CENTER);
        refresh();
        UI.addLogoutBar(this, cardPanel, system);
    }

    private void refresh() {
        model.setRowCount(0);
        Branch myBranch = system.getBranchDirectory().getList().stream()
                .filter(b -> b.getBranchManager()!=null && account.getEmployee()!=null
                        && Objects.equals(b.getBranchManager().getEmployeeId(), account.getEmployee().getEmployeeId()))
                .findFirst().orElse(null);
        for (RentalRecord r : system.getRentalDirectory().getList()) {
            if (myBranch==null || (r.getLibrary()!=null && r.getLibrary().equals(myBranch.getLibrary()))) {
                model.addRow(new Object[]{ r.getId(),
                        r.getBook()!=null ? r.getBook().getName() : "",
                        r.getCustomer()!=null ? r.getCustomer().getName() : "",
                        r.getRentedAt(), r.getReturnedAt(), r.getStatus() });
            }
        }
    }
}
