package ui;

import business.LibrarySystem;
import business.Role;
import business.UserAccount;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

/**
 * Simple login panel that authenticates against UserAccountDirectory.
 */
public class LoginPanel extends JPanel {

    private LibrarySystem system;
    private JPanel mainContainer;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginPanel(JFrame frame, LibrarySystem system, JPanel mainContainer) {
        this.system = system;
        this.mainContainer = mainContainer;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        JLabel lblTitle = new JLabel("Library Management System Login");
        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        JButton btnLogin = new JButton("Login");

        btnLogin.addActionListener(e -> doLogin());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(lblUser, gbc);
        gbc.gridx = 1;
        add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblPass, gbc);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(btnLogin, gbc);

        JLabel hint = new JLabel("Sample logins: admin/admin, bm1/bm1, bm2/bm2, c1/c1 ... c5/c5");
        gbc.gridy++;
        add(hint, gbc);
    }

    private void doLogin() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());
        UserAccount ua = system.getUserAccountDirectory().findUserAccount(user, pass);
        if (ua == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
            return;
        }

        Role role = ua.getRole();
        JPanel workArea = role.createWorkArea(mainContainer, ua, system);
        mainContainer.add("workarea", workArea);
        CardLayout cl = (CardLayout) mainContainer.getLayout();
        cl.show(mainContainer, "workarea");
    }
}
