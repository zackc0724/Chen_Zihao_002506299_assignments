package com.cafe.ui;

import cafe.directory.CustomerDirectory;
import cafe.directory.OrderDirectory;
import cafe.directory.ProductCatalog;
import com.cafe.model.*;
import java.awt.*;
import java.time.LocalDateTime;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final JPanel cardPanel = new JPanel(new CardLayout());
    private final ProductCatalog productCatalog = new ProductCatalog();
    private final CustomerDirectory customerDirectory = new CustomerDirectory();
    private final OrderDirectory orderDirectory = new OrderDirectory();

    public MainFrame() {
        super("Cafe POS — INFO5100 Assignment 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);
        seedInitialData();

        JPanel side = buildSidebar();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(side, BorderLayout.WEST);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Panels
        DashboardPanel dashboard = new DashboardPanel();
        ProductPanel productPanel = new ProductPanel(productCatalog);
        CustomerOrderPanel custOrderPanel = new CustomerOrderPanel(customerDirectory, productCatalog, orderDirectory);
        SearchPanel searchPanel = new SearchPanel(customerDirectory, orderDirectory, productCatalog);
        OrdersListPanel ordersListPanel = new OrdersListPanel(orderDirectory, customerDirectory, productCatalog);

        cardPanel.add(dashboard, "DASH");
        cardPanel.add(productPanel, "PROD");
        cardPanel.add(custOrderPanel, "CUST");
        cardPanel.add(searchPanel, "SEARCH");
        cardPanel.add(ordersListPanel, "ORDERS");

        showCard("DASH");
    }

    private JPanel buildSidebar() {
        JPanel side = new JPanel();
        side.setLayout(new GridLayout(0, 1, 8, 8));
        side.setBorder(BorderFactory.createEmptyBorder(16, 12, 16, 12));
        side.setBackground(new Color(245,245,245));

        JButton btnDash = new JButton("🏠 Dashboard");
        JButton btnProd = new JButton("☕ Manage Products");
        JButton btnCust = new JButton("🧑‍🤝‍🧑 Customers & Orders");
        JButton btnSearch = new JButton("🔎 Search Customers");
        JButton btnOrders = new JButton("🧾 List Orders");
        JButton btnExit = new JButton("Exit");

        btnDash.addActionListener(e -> showCard("DASH"));
        btnProd.addActionListener(e -> showCard("PROD"));
        btnCust.addActionListener(e -> showCard("CUST"));
        btnSearch.addActionListener(e -> showCard("SEARCH"));
        btnOrders.addActionListener(e -> showCard("ORDERS"));
        btnExit.addActionListener(e -> System.exit(0));

        side.add(btnDash);
        side.add(btnProd);
        side.add(btnCust);
        side.add(btnSearch);
        side.add(btnOrders);
        side.add(Box.createVerticalStrut(8));
        side.add(btnExit);
        return side;
    }

    private void showCard(String name) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, name);
    }

    private void seedInitialData() {
        // Products (>=5) with duplicate names
        productCatalog.add(new Product(101, "Latte", Category.COFFEE, 4.75, 1, 4));
        productCatalog.add(new Product(102, "Latte", Category.COFFEE, 5.25, 1, 6)); // duplicate name
        productCatalog.add(new Product(103, "Green Tea", Category.TEA, 3.25, 1, 3));
        productCatalog.add(new Product(104, "Blueberry Muffin", Category.PASTRY, 2.95, 1, 8));
        productCatalog.add(new Product(105, "Turkey Sandwich", Category.SANDWICH, 7.99, 1, 10));

        // Customers
        Customer a = new Customer(1, "Alice", "Nguyen", 6175550101L);
        Customer b = new Customer(2, "Bob", "Chen", 8575550202L);
        Customer c = new Customer(3, "Alice", "Wang", 7815550303L); // same first name

        customerDirectory.add(a);
        customerDirectory.add(b);
        customerDirectory.add(c);

        // Orders (>=3) one per customer; assign products
        Order o1 = new Order(5001, LocalDateTime.now().minusMinutes(30), OrderType.DINE_IN, PaymentMethod.CARD, OrderStatus.PREPARING, productCatalog.getById(101));
        orderDirectory.add(o1);
        a.setActiveOrder(o1);

        Order o2 = new Order(5002, LocalDateTime.now().minusMinutes(15), OrderType.TAKEOUT, PaymentMethod.CASH, OrderStatus.PENDING, productCatalog.getById(104));
        orderDirectory.add(o2);
        b.setActiveOrder(o2);

        Order o3 = new Order(5003, LocalDateTime.now().minusMinutes(5), OrderType.PICKUP, PaymentMethod.MOBILE, OrderStatus.READY, productCatalog.getById(102));
        orderDirectory.add(o3);
        c.setActiveOrder(o3);
    }
}
