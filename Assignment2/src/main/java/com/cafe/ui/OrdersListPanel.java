package com.cafe.ui;

import com.cafe.directory.OrderDirectory;
import com.cafe.directory.CustomerDirectory;
import com.cafe.directory.ProductCatalog;
import com.cafe.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrdersListPanel extends JPanel {
    private final OrderDirectory orders;
    private final CustomerDirectory customers;
    private final ProductCatalog products;
    private final JTable table = new JTable();

    public OrdersListPanel(OrderDirectory orders, CustomerDirectory customers, ProductCatalog products) {
        this.orders = orders;
        this.customers = customers;
        this.products = products;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Orders List");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        table.setModel(new DefaultTableModel(new Object[]{"Customer ID","Order ID","Product","Price"}, 0));
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnView = new JButton("View");
        JButton btnDelete = new JButton("Delete");
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(btnView); south.add(btnDelete);
        add(south, BorderLayout.SOUTH);

        btnView.addActionListener(e -> viewSelected());
        btnDelete.addActionListener(e -> deleteSelected());
    }

    private void refreshTable() {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        for (Order o : orders.getAll()) {
            Integer cid = null;
            for (Customer c : customers.getAll()) if (c.getActiveOrder()==o) { cid = c.getId(); break; }
            String prod = o.getProduct()!=null ? o.getProduct().getName() : "";
            double price = o.getProduct()!=null ? o.getProduct().getPrice() : 0;
            m.addRow(new Object[]{cid, o.getOrderId(), prod, price});
        }
    }

    private void viewSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        Integer oid = (Integer) table.getValueAt(row, 1);
        Order o = orders.findById(oid);
        JOptionPane.showMessageDialog(this, "Order #" + o.getOrderId() + "\nStatus: " + o.getStatus()
                + "\nType: " + o.getType() + "\nPayment: " + o.getPaymentMethod()
                + "\nProduct: " + (o.getProduct()!=null?o.getProduct().toString():""));
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected order?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        Integer oid = (Integer) table.getValueAt(row, 1);
        Order toDel = orders.findById(oid);
        if (toDel != null) orders.remove(toDel);
        // also clear activeOrder on matching customer
        for (Customer c : customers.getAll()) if (c.getActiveOrder()==toDel) c.setActiveOrder(null);
        refreshTable();
    }
}
