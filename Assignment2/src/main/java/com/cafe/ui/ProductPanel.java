package com.cafe.ui;

import cafe.directory.ProductCatalog;
import com.cafe.model.Category;
import com.cafe.model.Product;
import com.cafe.util.Validators;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductPanel extends JPanel {
    private final ProductCatalog catalog;
    private final JTable table = new JTable();
    private final JTextField tfId = new JTextField();
    private final JTextField tfName = new JTextField();
    private final JComboBox<Category> cbCat = new JComboBox<>(Category.values());
    private final JTextField tfPrice = new JTextField();
    private final JTextField tfNumber = new JTextField();
    private final JTextField tfPrep = new JTextField();

    public ProductPanel(ProductCatalog catalog) {
        this.catalog = catalog;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Manage Products");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        table.setModel(new DefaultTableModel(new Object[]{"ID", "Name", "Category", "Price", "Number", "Prep (min)"}, 0));
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 4, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Product Details"));
        form.add(new JLabel("Product ID:"));
        form.add(tfId);
        form.add(new JLabel("Product Name:"));
        form.add(tfName);
        form.add(new JLabel("Category:"));
        form.add(cbCat);
        form.add(new JLabel("Price:"));
        form.add(tfPrice);
        form.add(new JLabel("Number:"));
        form.add(tfNumber);
        form.add(new JLabel("Preparation Minutes:"));
        form.add(tfPrep);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update Selected");
        JButton btnDelete = new JButton("Delete Selected");
        JButton btnClear = new JButton("Clear");

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(btnClear); actions.add(btnAdd); actions.add(btnUpdate); actions.add(btnDelete);

        JPanel south = new JPanel(new BorderLayout());
        south.add(form, BorderLayout.CENTER);
        south.add(actions, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        btnClear.addActionListener(e -> { tfId.setText(""); tfName.setText(""); tfPrice.setText(""); tfNumber.setText(""); tfPrep.setText(""); });
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateSelected());
        btnDelete.addActionListener(e -> deleteSelected());
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelected());
    }

    private void refreshTable() {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        List<Product> list = catalog.getAll();
        for (Product p : list) {
            m.addRow(new Object[]{p.getId(), p.getName(), p.getCategory(), p.getPrice(), p.getNumber(), p.getPrepMinutes()});
        }
    }

    private void fillFormFromSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        tfId.setText(String.valueOf(table.getValueAt(row,0)));
        tfName.setText(String.valueOf(table.getValueAt(row,1)));
        cbCat.setSelectedItem(Category.valueOf(String.valueOf(table.getValueAt(row,2))));
        tfPrice.setText(String.valueOf(table.getValueAt(row,3)));
        tfNumber.setText(String.valueOf(table.getValueAt(row,4)));
        tfPrep.setText(String.valueOf(table.getValueAt(row,5)));
    }

    private void addProduct() {
        if (!Validators.requireText(tfId, "Product ID") || !Validators.requireText(tfName, "Product Name")
            || !Validators.requireText(tfPrice, "Price") || !Validators.requireText(tfNumber, "Number")
            || !Validators.requireText(tfPrep, "Preparation Minutes")) return;

        Integer id = Validators.parseInt(tfId, "Product ID");
        Double price = Validators.parseDouble(tfPrice, "Price");
        Integer number = Validators.parseInt(tfNumber, "Number");
        Integer prep = Validators.parseInt(tfPrep, "Preparation Minutes");
        if (id==null || price==null || number==null || prep==null) return;

        Product p = new Product(id, tfName.getText().trim(), (Category)cbCat.getSelectedItem(), price, number, prep);
        catalog.add(p);
        refreshTable();
        JOptionPane.showMessageDialog(this, "Product added.");
    }

    private void updateSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row first."); return; }
        Integer id = Validators.parseInt(tfId, "Product ID");
        Double price = Validators.parseDouble(tfPrice, "Price");
        Integer number = Validators.parseInt(tfNumber, "Number");
        Integer prep = Validators.parseInt(tfPrep, "Preparation Minutes");
        if (id==null || price==null || number==null || prep==null) return;
        String name = tfName.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Name required."); return; }

        // Find product by id and update
        for (Product p : catalog.getAll()) {
            if (p.getId() == ((Number)table.getValueAt(row,0)).intValue()) {
                p.setId(id);
                p.setName(name);
                p.setCategory((com.cafe.model.Category) cbCat.getSelectedItem());
                p.setPrice(price);
                p.setNumber(number);
                p.setPrepMinutes(prep);
                break;
            }
        }
        refreshTable();
        JOptionPane.showMessageDialog(this, "Product updated.");
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row first."); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected product?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = ((Number)table.getValueAt(row,0)).intValue();
        Product toDel = null;
        for (Product p : catalog.getAll()) if (p.getId()==id) { toDel = p; break; }
        if (toDel != null) catalog.remove(toDel);
        refreshTable();
    }
}
