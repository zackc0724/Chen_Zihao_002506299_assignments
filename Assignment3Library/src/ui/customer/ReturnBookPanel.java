package ui.customer;

import business.EcoSystem;
import business.items.Book;
import business.rental.RentalRecord;
import business.rental.RentalStatus;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReturnBookPanel extends JPanel {
    private final EcoSystem system;
    private final UserAccount account;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"RentalID","Book","Rented At"},0){
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JButton btnReturn = new JButton("Return Selected");

    public ReturnBookPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.system = system;
        this.account = account;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnReturn, BorderLayout.SOUTH);

        btnReturn.addActionListener(e -> doReturn());
        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        List<RentalRecord> mine = system.getRentalDirectory().getList().stream()
            .filter(r -> r.getCustomer()!=null && account.getCustomer()!=null 
                && r.getCustomer().getCustomerId()==account.getCustomer().getCustomerId()
                && r.getStatus()==RentalStatus.RENTED)
            .collect(Collectors.toList());
        for (RentalRecord r : mine) {
            model.addRow(new Object[]{ r.getId(), r.getBook()!=null ? r.getBook().getName() : "", r.getRentedAt() });
        }
    }

    private void doReturn() {
        int row = table.getSelectedRow();
        if(row<0) { JOptionPane.showMessageDialog(this, "Select a rental to return"); return; }
        // Find the RentalRecord by ID from the table model
        int rentalId = (int) model.getValueAt(row, 0);
        RentalRecord rec = system.getRentalDirectory().getList().stream()
            .filter(r -> r.getId()==rentalId).findFirst().orElse(null);
        if(rec==null) return;
        if(rec.getBook()!=null) rec.getBook().markReturned();
        rec.setReturnedAt(LocalDateTime.now());
        rec.setStatus(RentalStatus.RETURNED);
        refresh();
    }
}
