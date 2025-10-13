package ui.admin;

import business.EcoSystem;
import business.people.Customer;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.common.UI;

public class ManageCustomersPanel extends JPanel {
    private final EcoSystem system;
    private final JPanel cardPanel;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name"},0);
    private final JTable table = new JTable(model);
    private final JTextField txtName = new JTextField(12);

    public ManageCustomersPanel(JPanel cardPanel, EcoSystem system) {
        this.system = system;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel north = new JPanel();
        north.add(new JLabel("Customer Name:")); north.add(txtName);
        JButton btnAdd = new JButton("Add");
        north.add(btnAdd);
        add(north, BorderLayout.NORTH);

        btnAdd.addActionListener(e -> {
            if(txtName.getText().trim().isEmpty()) return;
            Customer c = new Customer(txtName.getText().trim());
            system.getCustomerDirectory().add(c);
            txtName.setText("");
            refresh();
        });
        refresh();
        UI.addLogoutBar(this, cardPanel, system);
    }

    private void refresh() {
        model.setRowCount(0);
        for (Customer c : system.getCustomerDirectory().getList()) {
            model.addRow(new Object[]{ c.getCustomerId(), c.getName() });
        }
    }
}
