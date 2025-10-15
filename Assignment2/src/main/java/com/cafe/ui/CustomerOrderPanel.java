package com.cafe.ui;

import cafe.directory.CustomerDirectory;
import cafe.directory.OrderDirectory;
import cafe.directory.ProductCatalog;
import com.cafe.model.*;
import com.cafe.util.Validators;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class CustomerOrderPanel extends JPanel {
    private final CustomerDirectory customers;
    private final ProductCatalog products;
    private final OrderDirectory orders;

    private final JTextField tfCid = new JTextField();
    private final JTextField tfFirst = new JTextField();
    private final JTextField tfLast = new JTextField();
    private final JTextField tfContact = new JTextField();

    private final JTextField tfOid = new JTextField();
    private final JComboBox<OrderType> cbType = new JComboBox<>(OrderType.values());
    private final JComboBox<PaymentMethod> cbPay = new JComboBox<>(PaymentMethod.values());
    private final JComboBox<OrderStatus> cbStatus = new JComboBox<>(OrderStatus.values());
    private final JComboBox<Product> cbProduct = new JComboBox<>();

    private final JTable tblOrders = new JTable();

    public CustomerOrderPanel(CustomerDirectory customers, ProductCatalog products, OrderDirectory orders) {
        this.customers = customers;
        this.products = products;
        this.orders = orders;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Customers & Orders");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0,4,8,8));
        form.setBorder(BorderFactory.createTitledBorder("Create/Update Customer & Order"));
        form.add(new JLabel("Customer ID:")); form.add(tfCid);
        form.add(new JLabel("First Name:")); form.add(tfFirst);
        form.add(new JLabel("Last Name:")); form.add(tfLast);
        form.add(new JLabel("Contact:")); form.add(tfContact);

        form.add(new JLabel("Order ID:")); form.add(tfOid);
        form.add(new JLabel("Order Type:")); form.add(cbType);
        form.add(new JLabel("Payment Method:")); form.add(cbPay);
        form.add(new JLabel("Order Status:")); form.add(cbStatus);
        form.add(new JLabel("Product Opted:")); 
        cbProduct.setRenderer(new DefaultListCellRenderer());
        for (Product p : products.getAll()) cbProduct.addItem(p);
        form.add(cbProduct);

        JButton btnSave = new JButton("Save (Add/Update)");
        JButton btnClear = new JButton("Clear");
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(btnClear); actions.add(btnSave);

        JPanel north = new JPanel(new BorderLayout());
        north.add(form, BorderLayout.CENTER);
        north.add(actions, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        tblOrders.setModel(new DefaultTableModel(new Object[]{"Customer ID","Order ID","Product","Price"}, 0));
        refreshOrdersTable();
        add(new JScrollPane(tblOrders), BorderLayout.CENTER);

        JButton btnView = new JButton("View Selected");
        JButton btnDelete = new JButton("Delete Selected");
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(btnView); south.add(btnDelete);
        add(south, BorderLayout.SOUTH);

        btnClear.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> saveCustomerOrder());
        btnView.addActionListener(e -> loadSelected());
        btnDelete.addActionListener(e -> deleteSelectedOrder());
    }

    private void clearForm() {
        tfCid.setText(""); tfFirst.setText(""); tfLast.setText(""); tfContact.setText("");
        tfOid.setText(""); cbType.setSelectedIndex(0); cbPay.setSelectedIndex(0); cbStatus.setSelectedIndex(0);
        if (cbProduct.getItemCount() > 0) cbProduct.setSelectedIndex(0);
    }

    private void saveCustomerOrder() {
        if (!Validators.requireText(tfCid, "Customer ID") || !Validators.requireText(tfFirst, "First Name")
            || !Validators.requireText(tfLast, "Last Name") || !Validators.requireText(tfContact, "Contact")
            || !Validators.requireText(tfOid, "Order ID")) return;

        Integer cid = Validators.parseInt(tfCid, "Customer ID");
        Long contact = Validators.parseLong(tfContact, "Contact");
        Integer oid = Validators.parseInt(tfOid, "Order ID");
        if (cid==null || contact==null || oid==null) return;

        String first = tfFirst.getText().trim();
        String last = tfLast.getText().trim();

        Customer existing = customers.findById(cid);
        if (existing == null) {
            existing = new Customer(cid, first, last, contact);
            customers.add(existing);
        } else {
            existing.setFirstName(first);
            existing.setLastName(last);
            existing.setContact(contact);
        }

        Product prod = (Product) cbProduct.getSelectedItem();
        Order order = new Order(oid, LocalDateTime.now(), (OrderType) cbType.getSelectedItem(),
                (PaymentMethod) cbPay.getSelectedItem(), (OrderStatus) cbStatus.getSelectedItem(), prod);
        orders.add(order);
        existing.setActiveOrder(order);

        refreshOrdersTable();
        JOptionPane.showMessageDialog(this, "Saved.");
    }

    private void refreshOrdersTable() {
        DefaultTableModel m = (DefaultTableModel) tblOrders.getModel();
        m.setRowCount(0);
        for (Order o : orders.getAll()) {
            m.addRow(new Object[]{findCustomerIdForOrder(o), o.getOrderId(), o.getProduct()!=null?o.getProduct().getName():"", o.getProduct()!=null?o.getProduct().getPrice():0});
        }
    }

    private Integer findCustomerIdForOrder(Order o) {
        for (Customer c : customers.getAll()) if (c.getActiveOrder() == o) return c.getId();
        return null;
    }

    private void loadSelected() {
        int row = tblOrders.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        Integer cid = (Integer) tblOrders.getValueAt(row, 0);
        Integer oid = (Integer) tblOrders.getValueAt(row, 1);
        Customer c = customers.findById(cid);
        tfCid.setText(String.valueOf(c.getId()));
        tfFirst.setText(c.getFirstName());
        tfLast.setText(c.getLastName());
        tfContact.setText(String.valueOf(c.getContact()));
        Order o = null;
        for (Order x : orders.getAll()) if (x.getOrderId()==oid) { o = x; break; }
        if (o != null) {
            tfOid.setText(String.valueOf(o.getOrderId()));
            cbType.setSelectedItem(o.getType());
            cbPay.setSelectedItem(o.getPaymentMethod());
            cbStatus.setSelectedItem(o.getStatus());
            if (o.getProduct()!=null) cbProduct.setSelectedItem(o.getProduct());
        }
    }

    private void deleteSelectedOrder() {
        int row = tblOrders.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected order?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        Integer oid = (Integer) tblOrders.getValueAt(row, 1);
        Order toDel = null;
        for (Order o : orders.getAll()) if (o.getOrderId()==oid) { toDel = o; break; }
        if (toDel != null) orders.remove(toDel);
        for (Customer c : customers.getAll()) if (c.getActiveOrder()==toDel) c.setActiveOrder(null);
        refreshOrdersTable();
    }
}
