package ui.common;

import business.EcoSystem;
import javax.swing.*;
import java.awt.BorderLayout;
import ui.MainJFrame;

public final class UI {
    private UI() {}
    public static void addLogoutBar(JPanel host, JPanel cardPanel, EcoSystem system) {
        JPanel south = new JPanel(new BorderLayout());
        JLabel status = new JLabel("Ready");
        JButton btnLogout = new JButton("Logout");

        south.add(status, BorderLayout.WEST);
        south.add(btnLogout, BorderLayout.EAST);
        host.add(south, BorderLayout.SOUTH);

        btnLogout.setMnemonic('L'); // Alt+L
        btnLogout.addActionListener(e -> {
            cardPanel.removeAll();
            cardPanel.revalidate();
            cardPanel.repaint();
            MainJFrame.insertTo(cardPanel, new LoginPanel(cardPanel, system));
        });
    }
}
        

