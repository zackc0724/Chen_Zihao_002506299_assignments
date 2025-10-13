package ui.admin;

import business.EcoSystem;
import business.people.Customer;
import business.people.Employee;
import business.roles.BranchManagerRole;
import business.roles.CustomerRole;
import business.roles.SystemAdminRole;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.common.UI;

public class ManageUserAccountsPanel extends JPanel {
    private final EcoSystem system;
    private final JPanel cardPanel;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Username","Role","Customer","Employee"},0);
    private final JTable table = new JTable(model);

    private final JTextField txtUser = new JTextField(10);
    private final JTextField txtPass = new JTextField(10);
    private final JComboBox<String> cmbRole = new JComboBox<>(new String[]{"SystemAdmin","BranchManager","Customer"});
    private final JComboBox<Customer> cmbCustomer;
    private final JComboBox<Employee> cmbEmployee;

    public ManageUserAccountsPanel(JPanel cardPanel, EcoSystem system) {
        this.system = system;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        cmbCustomer = new JComboBox<>(system.getCustomerDirectory().getList().toArray(new Customer[0]));
        cmbEmployee = new JComboBox<>(system.getEmployeeDirectory().getList().toArray(new Employee[0]));

        JPanel north = new JPanel();
        north.add(new JLabel("User:")); north.add(txtUser);
        north.add(new JLabel("Pass:")); north.add(txtPass);
        north.add(new JLabel("Role:")); north.add(cmbRole);
        north.add(new JLabel("Customer:")); north.add(cmbCustomer);
        north.add(new JLabel("Employee:")); north.add(cmbEmployee);
        JButton btnAdd = new JButton("Create");
        north.add(btnAdd);
        add(north, BorderLayout.NORTH);

        btnAdd.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String p = txtPass.getText().trim();
            if(u.isEmpty() || p.isEmpty()) { JOptionPane.showMessageDialog(this, "Username and password required"); return; }
            UserAccount ua;
            switch((String)cmbRole.getSelectedItem()) {
                case "BranchManager":
                    ua = new UserAccount(u,p,new BranchManagerRole());
                    ua.setEmployee((Employee)cmbEmployee.getSelectedItem());
                    break;
                case "Customer":
                    ua = new UserAccount(u,p,new CustomerRole());
                    ua.setCustomer((Customer)cmbCustomer.getSelectedItem());
                    break;
                default:
                    ua = new UserAccount(u,p,new SystemAdminRole());
            }
            system.getUserAccountDirectory().add(ua);
            txtUser.setText(""); txtPass.setText("");
            refresh();
        });

        refresh();
        UI.addLogoutBar(this, cardPanel, system);
    }

    private void refresh() {
        model.setRowCount(0);
        for (UserAccount ua : system.getUserAccountDirectory().getList()) {
            model.addRow(new Object[]{ ua.getUsername(), ua.getRole().toString(),
              ua.getCustomer(), ua.getEmployee() });
        }
    }
}
