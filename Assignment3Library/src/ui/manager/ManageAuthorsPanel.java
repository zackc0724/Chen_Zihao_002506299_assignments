package ui.manager;

import business.EcoSystem;
import business.items.Author;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageAuthorsPanel extends JPanel {
    private final EcoSystem system;
    private final UserAccount account;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name"},0);
    private final JTable table = new JTable(model);
    private final JTextField txtName = new JTextField(12);

    public ManageAuthorsPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.system = system;
        this.account = account;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel();
        south.add(new JLabel("Author Name:")); south.add(txtName);
        JButton btnAdd = new JButton("Add");
        south.add(btnAdd);
        add(south, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            if(txtName.getText().trim().isEmpty()) return;
            Author a = new Author(txtName.getText().trim());
            system.getAuthorDirectory().add(a);
            refresh();
        });

        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        for (Author a : system.getAuthorDirectory().getList()) {
            model.addRow(new Object[]{ a.getAuthorId(), a.getName() });
        }
    }
}
