package com.cafe.ui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel title = new JLabel("Cafe POS — Dashboard", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        add(title, BorderLayout.NORTH);

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setText("Use the sidebar to manage Products, Customers & Orders, search customers, "
                + "and list orders.\nThis app uses CardLayout and enforces validations.");
        add(new JScrollPane(info), BorderLayout.CENTER);
    }
}
