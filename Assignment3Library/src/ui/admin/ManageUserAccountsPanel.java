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

public class ManageUserAccountsPanel extends JPanel {
    private final EcoSystem system;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Username","Role","Customer","Employee"},0);
    private final JTable table = new JTable(model);

    private final JTextField txtUser = new JTextField(10);
    private final JTextField txtPass = new JTextField(10);
    private final JComboBox<String> cmbRole = new JComboBox<>(new String[]{"SystemAdmin","BranchManager","Customer"});
    private final JComboBox<Customer> cmbCustomer;
    private final JComboBox<Employee> cmbEmployee;

    public ManageUserAccountsPanel(JPanel cardPanel, EcoSystem system) {
        this.system = system;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        cmbCustomer = new JComboBox<>(system.getCustomerDirectory().getList().toArray(new Customer[0]));
        cmbEmployee = new JComboBox<>(system.getEmployeeDirectory().getList().toArray(new Employee[0]));

        JPanel south = new JPanel();
        south.add(new JLabel("User:")); south.add(txtUser);
        south.add(new JLabel("Pass:")); south.add(txtPass);
        south.add(new JLabel("Role:")); south.add(cmbRole);
        south.add(new JLabel("Customer:")); south.add(cmbCustomer);
        south.add(new JLabel("Employee:")); south.add(cmbEmployee);
        JButton btnAdd = new JButton("Create");
        south.add(btnAdd);
        add(south, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String p = txtPass.getText().trim();
            if(u.isEmpty()||p.isEmpty()) return;
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
            refresh();
        });

        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        for (UserAccount ua : system.getUserAccountDirectory().getList()) {
            model.addRow(new Object[]{ ua.getUsername(), ua.getRole().toString(),
              ua.getCustomer(), ua.getEmployee() });
        }
    }
}
