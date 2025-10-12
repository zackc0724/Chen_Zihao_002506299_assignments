package ui.common;

import business.EcoSystem;
import business.useraccount.UserAccount;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import ui.MainJFrame;

public class LoginPanel extends JPanel {
    private final JPanel cardPanel;
    private final EcoSystem system;

    private final JTextField txtUser = new JTextField(16);
    private final JPasswordField txtPass = new JPasswordField(16);

    public LoginPanel(JPanel cardPanel, EcoSystem system) {
        this.cardPanel = cardPanel;
        this.system = system;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.gridx=0; gc.gridy=0; add(new JLabel("Username:"), gc);
        gc.gridx=1; add(txtUser, gc);
        gc.gridx=0; gc.gridy=1; add(new JLabel("Password:"), gc);
        gc.gridx=1; add(txtPass, gc);
        JButton btn = new JButton("Login");
        gc.gridx=1; gc.gridy=2; add(btn, gc);

        btn.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword());
        UserAccount ua = system.getUserAccountDirectory().find(u, p);
        if (ua == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
            return;
        }
        MainJFrame.insertTo(cardPanel, ua.getRole().createWorkArea(cardPanel, system, ua));
    }
}
