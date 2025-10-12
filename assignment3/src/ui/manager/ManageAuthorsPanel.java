package ui.manager;

import business.EcoSystem;
import business.items.Author;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.common.UI;

public class ManageAuthorsPanel extends JPanel {
    private final EcoSystem system;
    private final UserAccount account;
    private final JPanel cardPanel;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name"},0){
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtName = new JTextField(20);

    public ManageAuthorsPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.system = system;
        this.account = account;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout(6,6));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        top.add(new JLabel("Author Name:"));
        top.add(txtName);
        JButton btnAdd = new JButton("Add Author");
        top.add(btnAdd);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            String n = txtName.getText().trim();
            if (n.isEmpty()) return;
            Author a = new Author(n);
            system.getAuthorDirectory().add(a);
            txtName.setText("");
            refresh();
        });

        refresh();
        UI.addLogoutBar(this, cardPanel, system);
    }

    private void refresh() {
        model.setRowCount(0);
        for (Author a : system.getAuthorDirectory().getList()) {
            model.addRow(new Object[]{ a.getAuthorId(), a.getName() });
        }
    }
}
