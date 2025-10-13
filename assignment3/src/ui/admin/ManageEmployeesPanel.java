package ui.admin;

import business.EcoSystem;
import business.org.Branch;
import business.people.Employee;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.common.UI;

public class ManageEmployeesPanel extends JPanel {
    private final EcoSystem system;
    private final JPanel cardPanel;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Branch","Manager"},0);
    private final JTable table = new JTable(model);
    private final JComboBox<Branch> cmbBranch;
    private final JTextField txtName = new JTextField(12);
    private final JSpinner spYears = new JSpinner(new SpinnerNumberModel(1,0,60,1));

    public ManageEmployeesPanel(JPanel cardPanel, EcoSystem system) {
        this.system = system;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        cmbBranch = new JComboBox<>(system.getBranchDirectory().getList().toArray(new Branch[0]));

        JPanel north = new JPanel();
        north.add(new JLabel("Branch:")); north.add(cmbBranch);
        north.add(new JLabel("Manager Name:")); north.add(txtName);
        north.add(new JLabel("Years:")); north.add(spYears);
        JButton btnAssign = new JButton("Assign Manager");
        north.add(btnAssign);
        add(north, BorderLayout.NORTH);

        btnAssign.addActionListener(e -> {
            Branch b = (Branch) cmbBranch.getSelectedItem();
            if(b==null) return;
            Employee m = new Employee(txtName.getText().trim(), (int)spYears.getValue());
            system.getEmployeeDirectory().add(m);
            b.setBranchManager(m);
            refresh();
        });

        refresh();
        UI.addLogoutBar(this, cardPanel, system);
    }

    private void refresh() {
        model.setRowCount(0);
        for (Branch b : system.getBranchDirectory().getList()) {
            model.addRow(new Object[]{ b.getName(), b.getBranchManager()!=null?b.getBranchManager().getName():"(none)"});
        }
    }
}
