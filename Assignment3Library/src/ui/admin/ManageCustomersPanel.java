package ui.admin;

import business.EcoSystem;
import business.people.Customer;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageCustomersPanel extends JPanel {
    private final EcoSystem system;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name"},0);
    private final JTable table = new JTable(model);
    private final JTextField txtName = new JTextField(12);

    public ManageCustomersPanel(JPanel cardPanel, EcoSystem system) {
        this.system = system;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel();
        south.add(new JLabel("Customer Name:")); south.add(txtName);
        JButton btnAdd = new JButton("Add");
        south.add(btnAdd);
        add(south, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            if(txtName.getText().trim().isEmpty()) return;
            Customer c = new Customer(txtName.getText().trim());
            system.getCustomerDirectory().add(c);
            refresh();
        });

        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        for (Customer c : system.getCustomerDirectory().getList()) {
            model.addRow(new Object[]{ c.getCustomerId(), c.getName() });
        }
    }
}
