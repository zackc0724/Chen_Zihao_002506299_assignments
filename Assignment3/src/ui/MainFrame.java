package ui;

import business.LibrarySystem;
import business.Role;
import business.UserAccount;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Main JFrame with login panel and CardLayout work areas.
 */
public class MainFrame extends JFrame {

    private LibrarySystem system;
    private JPanel mainContainer;

    public MainFrame() {
        this.system = LibrarySystem.configure();
        initComponents();
    }

    private void initComponents() {
        setTitle("INFO5100 Assignment 3 - Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        mainContainer = new JPanel(new CardLayout());
        LoginPanel login = new LoginPanel(this, system, mainContainer);

        mainContainer.add("login", login);
        add(mainContainer);
    }

    public void showPanel(String name, JPanel panel) {
        mainContainer.add(name, panel);
        CardLayout cl = (CardLayout) mainContainer.getLayout();
        cl.show(mainContainer, name);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
