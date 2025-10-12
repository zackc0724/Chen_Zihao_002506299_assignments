package ui;

import business.ConfigureTheBusiness;
import business.EcoSystem;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.common.LoginPanel;

public class MainJFrame extends javax.swing.JFrame {
    private final JPanel cardPanel = new javax.swing.JPanel(new CardLayout());
    private EcoSystem system;

    public MainJFrame() {
        setTitle("Assignment 3 Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(cardPanel, java.awt.BorderLayout.CENTER);

        system = ConfigureTheBusiness.configure();
        showLogin();
    }

    private void showLogin() {
        insert(new LoginPanel(cardPanel, system));
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new MainJFrame().setVisible(true));
    }

    public static void insertTo(JPanel cardPanel, JPanel panel) {
        String key = panel.getClass().getSimpleName() + "_" + System.nanoTime();
        cardPanel.add(panel, key);
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, key);
    }

    private void insert(JPanel panel) { insertTo(cardPanel, panel); }
}
