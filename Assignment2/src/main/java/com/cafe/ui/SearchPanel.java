package com.cafe.ui;

import cafe.directory.CustomerDirectory;
import cafe.directory.OrderDirectory;
import cafe.directory.ProductCatalog;
import com.cafe.model.Customer;
import com.cafe.model.Order;
import com.cafe.model.Product;
import com.cafe.util.Validators;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchPanel extends JPanel {
    private final CustomerDirectory customers;
    private final OrderDirectory orders;
    private final ProductCatalog products;

    private final JTextField tfId = new JTextField();
    private final JTextField tfName = new JTextField();
    private final JTable tblResults = new JTable();

    public SearchPanel(CustomerDirectory customers, OrderDirectory orders, ProductCatalog products) {
        this.customers = customers;
        this.orders = orders;
        this.products = products;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Search Customers");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        JPanel criteria = new JPanel(new GridLayout(0,4,8,8));
        criteria.setBorder(BorderFactory.createTitledBorder("Criteria"));
        criteria.add(new JLabel("Customer ID:")); criteria.add(tfId);
        criteria.add(new JLabel("Name (full or partial):")); criteria.add(tfName);
        JButton btnById = new JButton("Search by ID");
        JButton btnByName = new JButton("Search by Name");
        JPanel critSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        critSouth.add(btnById); critSouth.add(btnByName);

        JPanel north = new JPanel(new BorderLayout());
        north.add(criteria, BorderLayout.CENTER);
        north.add(critSouth, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        tblResults.setModel(new DefaultTableModel(new Object[]{"ID","Name","Contact","Order ID","Product","Price"}, 0));
        add(new JScrollPane(tblResults), BorderLayout.CENTER);

        JButton btnUpdate = new JButton("Update Selected");
        JButton btnDelete = new JButton("Delete Selected Customer");
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(btnUpdate); south.add(btnDelete);
        add(south, BorderLayout.SOUTH);

        btnById.addActionListener(e -> searchById());
        btnByName.addActionListener(e -> searchByName());
        btnUpdate.addActionListener(e -> updateSelected());
        btnDelete.addActionListener(e -> deleteSelected());
    }

    private void searchById() {
        if (!Validators.requireText(tfId, "Customer ID")) return;
        Integer id = Validators.parseInt(tfId, "Customer ID");
        if (id == null) return;
        DefaultTableModel m = (DefaultTableModel) tblResults.getModel();
        m.setRowCount(0);
        Customer c = customers.findById(id);
        if (c != null) addRowFromCustomer(m, c);
    }

    private void searchByName() {
        if (!Validators.requireText(tfName, "Name")) return;
        DefaultTableModel m = (DefaultTableModel) tblResults.getModel();
        m.setRowCount(0);
        List<Customer> list = customers.findByName(tfName.getText().trim());
        for (Customer c : list) addRowFromCustomer(m, c);
    }

    private void addRowFromCustomer(DefaultTableModel m, Customer c) {
        Integer oid = null; String prod = ""; Double price = null;
        if (c.getActiveOrder()!=null) {
            oid = c.getActiveOrder().getOrderId();
            Product p = c.getActiveOrder().getProduct();
            if (p != null) { prod = p.getName(); price = p.getPrice(); }
        }
        m.addRow(new Object[]{c.getId(), c.getFirstName()+" "+c.getLastName(), c.getContact(), oid, prod, price});
    }

    private void updateSelected() {
        int row = tblResults.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        Integer id = ((Number)tblResults.getValueAt(row, 0)).intValue();
        Customer c = customers.findById(id);
        String name = JOptionPane.showInputDialog(this, "Update full name:", c.getFirstName()+" "+c.getLastName());
        if (name == null || name.trim().isEmpty()) return;
        String[] parts = name.trim().split("\s+", 2);
        c.setFirstName(parts[0]);
        if (parts.length > 1) c.setLastName(parts[1]);

        String contactStr = JOptionPane.showInputDialog(this, "Update contact (digits only):", String.valueOf(c.getContact()));
        try {
            long contact = Long.parseLong(contactStr.trim());
            c.setContact(contact);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Contact not updated (invalid).");
        }
        searchById(); // refresh if ID present, else refresh name search
    }

    private void deleteSelected() {
        int row = tblResults.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected customer?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        Integer id = ((Number)tblResults.getValueAt(row, 0)).intValue();
        Customer toDel = customers.findById(id);
        if (toDel != null) {
            if (toDel.getActiveOrder()!=null) {
                // optional: also remove order from directory
            }
            customers.remove(toDel);
            ((DefaultTableModel)tblResults.getModel()).removeRow(row);
        }
    }
}
