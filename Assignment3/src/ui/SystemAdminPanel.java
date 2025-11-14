package ui;

import business.Branch;
import business.Customer;
import business.Employee;
import business.LibrarySystem;
import business.CustomerRole;
import business.UserAccountDirectory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 * System admin panel for managing branches, employees, and customers.
 */
public class SystemAdminPanel extends JPanel {

    private LibrarySystem system;
    private JPanel mainContainer;
    private JTable tblBranches;
    private JTable tblEmployees;
    private JTable tblCustomers;

    private JTextField txtBranchName;
    private JTextField txtEmpName;
    private JTextField txtEmpExp;
    private JComboBox<Branch> cbBranchForEmp;

    private JTextField txtCustName;
    private JComboBox<Branch> cbBranchForCust;
    private JTextField txtCustUsername;
    private JTextField txtCustPassword;

    public SystemAdminPanel(JPanel mainContainer, LibrarySystem system) {
        this.mainContainer = mainContainer;
        this.system = system;
        initComponents();
        populateTables();
}

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel pnlBranches = new JPanel(new BorderLayout());
        pnlBranches.setBorder(BorderFactory.createTitledBorder("Branches"));

        tblBranches = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "Library Building"}));

        pnlBranches.add(new JScrollPane(tblBranches), BorderLayout.CENTER);

        JPanel pnlAddBranch = new JPanel();
        txtBranchName = new JTextField(15);
        JButton btnAddBranch = new JButton("Add Branch");
        JButton btnDeleteBranch = new JButton("Delete Selected Branch");

        btnAddBranch.addActionListener(e -> addBranch());
        btnDeleteBranch.addActionListener(e -> deleteBranch());

        pnlAddBranch.add(new JLabel("Name:"));
        pnlAddBranch.add(txtBranchName);
        pnlAddBranch.add(btnAddBranch);
        pnlAddBranch.add(btnDeleteBranch);

        pnlBranches.add(pnlAddBranch, BorderLayout.SOUTH);

        JPanel pnlEmployees = new JPanel(new BorderLayout());
        pnlEmployees.setBorder(BorderFactory.createTitledBorder("Branch Managers"));

        tblEmployees = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Emp ID", "Name", "Exp", "Branch"}));
        pnlEmployees.add(new JScrollPane(tblEmployees), BorderLayout.CENTER);

        JPanel pnlAddEmp = new JPanel();
        txtEmpName = new JTextField(10);
        txtEmpExp = new JTextField(5);
        cbBranchForEmp = new JComboBox<>();

        JButton btnAddEmp = new JButton("Add Manager");
        btnAddEmp.addActionListener(e -> addEmployee());

        pnlAddEmp.add(new JLabel("Name:"));
        pnlAddEmp.add(txtEmpName);
        pnlAddEmp.add(new JLabel("Experience:"));
        pnlAddEmp.add(txtEmpExp);
        pnlAddEmp.add(new JLabel("Branch:"));
        pnlAddEmp.add(cbBranchForEmp);
        pnlAddEmp.add(btnAddEmp);

        pnlEmployees.add(pnlAddEmp, BorderLayout.SOUTH);

        JPanel pnlCustomers = new JPanel(new BorderLayout());
        pnlCustomers.setBorder(BorderFactory.createTitledBorder("Customers"));

        tblCustomers = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Cust ID", "Name", "Branch", "Username"}));
        pnlCustomers.add(new JScrollPane(tblCustomers), BorderLayout.CENTER);

        JPanel pnlAddCust = new JPanel();
        txtCustName = new JTextField(10);
        cbBranchForCust = new JComboBox<>();
        txtCustUsername = new JTextField(8);
        txtCustPassword = new JTextField(8);
        JButton btnAddCust = new JButton("Register Customer");
        btnAddCust.addActionListener(e -> addCustomer());

        pnlAddCust.add(new JLabel("Name:"));
        pnlAddCust.add(txtCustName);
        pnlAddCust.add(new JLabel("Branch:"));
        pnlAddCust.add(cbBranchForCust);
        pnlAddCust.add(new JLabel("Username:"));
        pnlAddCust.add(txtCustUsername);
        pnlAddCust.add(new JLabel("Password:"));
        pnlAddCust.add(txtCustPassword);
        pnlAddCust.add(btnAddCust);

        pnlCustomers.add(pnlAddCust, BorderLayout.SOUTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.33;
        center.add(pnlBranches, gbc);
        gbc.gridy++;
        center.add(pnlEmployees, gbc);
        gbc.gridy++;
        center.add(pnlCustomers, gbc);
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
        CardLayout cl = (CardLayout) mainContainer.getLayout();
        cl.show(mainContainer, "login");
        });
add(btnBack, BorderLayout.NORTH);

        add(center, BorderLayout.CENTER);
    }

    private void populateTables() {
        DefaultTableModel bm = (DefaultTableModel) tblBranches.getModel();
        bm.setRowCount(0);
        cbBranchForEmp.removeAllItems();
        cbBranchForCust.removeAllItems();

        for (Branch b : system.getBranches()) {
            Object[] row = new Object[3];
            row[0] = b.getId();
            row[1] = b.getName();
            row[2] = b.getLibrary().getBuildingNo();
            bm.addRow(row);

            cbBranchForEmp.addItem(b);
            cbBranchForCust.addItem(b);
        }

        DefaultTableModel em = (DefaultTableModel) tblEmployees.getModel();
        em.setRowCount(0);
        for (Employee e : system.getEmployees()) {
            Object[] row = new Object[4];
            row[0] = e.getEmployeeId();
            row[1] = e.getName();
            row[2] = e.getExperience();
            row[3] = e.getBranch() != null ? e.getBranch().getName() : "";
            em.addRow(row);
        }

        DefaultTableModel cm = (DefaultTableModel) tblCustomers.getModel();
        cm.setRowCount(0);
        for (Customer c : system.getCustomers()) {
            Object[] row = new Object[4];
            row[0] = c.getCustomerId();
            row[1] = c.getName();
            row[2] = c.getBranch() != null ? c.getBranch().getName() : "";
            String username = "";
            for (business.UserAccount ua : system.getUserAccountDirectory().getAccounts()) {
                if (ua.getPerson() == c) {
                    username = ua.getUsername();
                    break;
                }
            }
            row[3] = username;
            cm.addRow(row);
        }
    }

    private void addBranch() {
        String name = txtBranchName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter branch name");
            return;
        }
        Branch b = new Branch(name);
        system.addBranch(b);
        txtBranchName.setText("");
        populateTables();
    }

    private void deleteBranch() {
        int row = tblBranches.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a branch to delete");
            return;
        }
        int id = (Integer) tblBranches.getValueAt(row, 0);
        Branch toRemove = null;
        for (Branch b : system.getBranches()) {
            if (b.getId() == id) {
                toRemove = b;
                break;
            }
        }
        if (toRemove != null) {
            system.removeBranch(toRemove);
            populateTables();
        }
    }

    private void addEmployee() {
        String name = txtEmpName.getText().trim();
        String expStr = txtEmpExp.getText().trim();
        Branch branch = (Branch) cbBranchForEmp.getSelectedItem();
        if (name.isEmpty() || expStr.isEmpty() || branch == null) {
            JOptionPane.showMessageDialog(this, "Fill all fields");
            return;
        }
        int exp;
        try {
            exp = Integer.parseInt(expStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Experience must be an integer");
            return;
        }
        Employee emp = new Employee(name, exp, branch);
        system.addEmployee(emp);
        branch.setBranchManager(emp);
        txtEmpName.setText("");
        txtEmpExp.setText("");
        populateTables();
    }

    private void addCustomer() {
        String name = txtCustName.getText().trim();
        String username = txtCustUsername.getText().trim();
        String password = txtCustPassword.getText().trim();
        Branch branch = (Branch) cbBranchForCust.getSelectedItem();
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || branch == null) {
            JOptionPane.showMessageDialog(this, "Fill all fields");
            return;
        }
        Customer c = new Customer(name, branch);
        system.addCustomer(c);
        system.getUserAccountDirectory().createUserAccount(username, password, c, new CustomerRole());
        txtCustName.setText("");
        txtCustUsername.setText("");
        txtCustPassword.setText("");
        populateTables();
    }
}
