package ui.admin;

import business.EcoSystem;
import business.org.Branch;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.common.UI;

public class ManageBranchesPanel extends JPanel {
    private final EcoSystem system;
    private final JPanel cardPanel;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Name","Library #","Manager"},0);
    private final JTable table = new JTable(model);
    private final JTextField txtName = new JTextField(12);
    private final JSpinner spLib = new JSpinner(new SpinnerNumberModel(100,1,9999,1));

    public ManageBranchesPanel(javax.swing.JPanel cardPanel, EcoSystem system) {
        this.system = system;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel north = new JPanel();
        north.add(new JLabel("Name:")); north.add(txtName);
        north.add(new JLabel("Library #:")); north.add(spLib);
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete Selected");
        north.add(btnAdd); north.add(btnDelete);
        add(north, BorderLayout.NORTH);

        btnAdd.addActionListener(e -> {
            String name = txtName.getText().trim();
            int lib = (int) spLib.getValue();
            if(name.isEmpty()) { JOptionPane.showMessageDialog(this,"Enter name"); return; }
            system.getBranchDirectory().add(new Branch(name, lib));
            refresh();
        });
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row < 0) { JOptionPane.showMessageDialog(this,"Select a row"); return; }
            int ok = JOptionPane.showConfirmDialog(this, "Delete selected branch?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
            if (ok != JOptionPane.OK_OPTION) return;

            Branch b = system.getBranchDirectory().getList().get(row);
            system.getBranchDirectory().remove(b);
            refresh();
        });

        UI.addLogoutBar(this, cardPanel, system);
    }

    private void refresh() {
        model.setRowCount(0);
        for (Branch b : system.getBranchDirectory().getList()) {
            model.addRow(new Object[]{ b.getName(), b.getLibrary().getBuildingNo(),
                b.getBranchManager()!=null?b.getBranchManager().getName():"(none)"});
        }
    }
}
