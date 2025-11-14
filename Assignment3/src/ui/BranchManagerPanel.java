package ui;

import business.Author;
import business.Book;
import business.Branch;
import business.Employee;
import business.Library;
import business.LibrarySystem;
import business.RentalRecord;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Branch manager can manage books, authors, and view rental records.
 */
public class BranchManagerPanel extends JPanel {

    private LibrarySystem system;
    private Employee manager;
    private Library library;

    private JTable tblBooks;
    private JTable tblRentals;
    private JTable tblAuthors;

    private JTextField txtBookName;
    private JTextField txtPages;
    private JTextField txtLanguage;
    private JComboBox<Author> cbAuthor;
    private JPanel mainContainer;
    private JTextField txtAuthorName;

    public BranchManagerPanel(JPanel mainContainer, LibrarySystem system, Employee manager) {
        this.mainContainer = mainContainer;
        this.system = system;
        this.manager = manager;
        Branch branch = manager.getBranch();
        this.library = branch.getLibrary();
        initComponents();
        populateAll();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // books panel
        JPanel pnlBooks = new JPanel(new BorderLayout());
        pnlBooks.setBorder(BorderFactory.createTitledBorder("Books in " + library));

        tblBooks = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Serial", "Name", "Author", "Pages", "Lang", "Rented"}));
        pnlBooks.add(new JScrollPane(tblBooks), BorderLayout.CENTER);

        JPanel pnlAddBook = new JPanel();
        txtBookName = new JTextField(10);
        txtPages = new JTextField(5);
        txtLanguage = new JTextField(6);
        cbAuthor = new JComboBox<>();

        JButton btnAddBook = new JButton("Add Book");
        btnAddBook.addActionListener(e -> addBook());

        pnlAddBook.add(new JLabel("Name:"));
        pnlAddBook.add(txtBookName);
        pnlAddBook.add(new JLabel("Author:"));
        pnlAddBook.add(cbAuthor);
        pnlAddBook.add(new JLabel("Pages:"));
        pnlAddBook.add(txtPages);
        pnlAddBook.add(new JLabel("Lang:"));
        pnlAddBook.add(txtLanguage);
        pnlAddBook.add(btnAddBook);

        pnlBooks.add(pnlAddBook, BorderLayout.SOUTH);

        JPanel pnlAuthors = new JPanel(new BorderLayout());
        pnlAuthors.setBorder(BorderFactory.createTitledBorder("Authors"));

        tblAuthors = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name"}));
        pnlAuthors.add(new JScrollPane(tblAuthors), BorderLayout.CENTER);

        JPanel pnlAddAuthor = new JPanel();
        txtAuthorName = new JTextField(15);
        JButton btnAddAuthor = new JButton("Add Author");
        btnAddAuthor.addActionListener(e -> addAuthor());

        pnlAddAuthor.add(new JLabel("Name:"));
        pnlAddAuthor.add(txtAuthorName);
        pnlAddAuthor.add(btnAddAuthor);
        pnlAuthors.add(pnlAddAuthor, BorderLayout.SOUTH);

        JPanel pnlRentals = new JPanel(new BorderLayout());
        pnlRentals.setBorder(BorderFactory.createTitledBorder("Rental Records"));

        tblRentals = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Book", "Customer", "Rented At", "Returned At", "Status"}));
        pnlRentals.add(new JScrollPane(tblRentals), BorderLayout.CENTER);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; gbc.weighty = 0.4;
        center.add(pnlBooks, gbc);
        gbc.gridy++;
        gbc.weighty = 0.2;
        center.add(pnlAuthors, gbc);
        gbc.gridy++;
        gbc.weighty = 0.4;
        center.add(pnlRentals, gbc);
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
        CardLayout cl = (CardLayout) mainContainer.getLayout();
        cl.show(mainContainer, "login");
        });
        add(btnBack, BorderLayout.NORTH);

        add(center, BorderLayout.CENTER);
    }

    private void populateAll() {
        DefaultTableModel bm = (DefaultTableModel) tblBooks.getModel();
        bm.setRowCount(0);
        for (Book b : library.getBooks()) {
            Object[] row = new Object[6];
            row[0] = b.getSerialNumber();
            row[1] = b.getName();
            row[2] = b.getAuthor() != null ? b.getAuthor().getName() : "";
            row[3] = b.getPages();
            row[4] = b.getLanguage();
            row[5] = b.isRented() ? "Yes" : "No";
            bm.addRow(row);
        }

        DefaultTableModel am = (DefaultTableModel) tblAuthors.getModel();
        am.setRowCount(0);
        cbAuthor.removeAllItems();
        for (Author a : system.getAuthors()) {
            Object[] row = new Object[2];
            row[0] = a.getId();
            row[1] = a.getName();
            am.addRow(row);
            cbAuthor.addItem(a);
        }

        DefaultTableModel rm = (DefaultTableModel) tblRentals.getModel();
        rm.setRowCount(0);
        for (RentalRecord r : system.getRentalRecords()) {
            if (r.getLibrary() == library) {
                Object[] row = new Object[6];
                row[0] = r.getId();
                row[1] = r.getBook().getName();
                row[2] = r.getCustomer().getName();
                row[3] = r.getRentedAt();
                row[4] = r.getReturnedAt();
                row[5] = r.getStatus();
                rm.addRow(row);
            }
        }
    }

    private void addBook() {
        String name = txtBookName.getText().trim();
        String pagesStr = txtPages.getText().trim();
        String lang = txtLanguage.getText().trim();
        Author author = (Author) cbAuthor.getSelectedItem();
        if (name.isEmpty() || pagesStr.isEmpty() || lang.isEmpty() || author == null) {
            return;
        }
        int pages;
        try {
            pages = Integer.parseInt(pagesStr);
        } catch (NumberFormatException ex) {
            return;
        }
        Book b = new Book(name, author, pages, lang);
        library.addBook(b);
        txtBookName.setText("");
        txtPages.setText("");
        txtLanguage.setText("");
        populateAll();
    }

    private void addAuthor() {
        String name = txtAuthorName.getText().trim();
        if (name.isEmpty()) {
            return;
        }
        Author a = new Author(name);
        system.addAuthor(a);
        txtAuthorName.setText("");
        populateAll();
    }
}
