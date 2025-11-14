package ui;

import business.Book;
import business.Branch;
import business.Customer;
import business.Library;
import business.LibrarySystem;
import business.RentalRecord;
import business.RentalStatus;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Customer work area:
 * - Browse all books in branch library
 * - Rent available books
 * - Return rented books
 * - View rental history
 */
public class CustomerPanel extends JPanel {

    private LibrarySystem system;
    private Customer customer;
    private Library library;
    private JPanel mainContainer;
    private JTable tblAllBooks;
    private JTable tblMyRentals;
    private JTable tblHistory;

    public CustomerPanel(JPanel mainContainer, LibrarySystem system, Customer customer) {
        this.mainContainer = mainContainer;
        this.system = system;
        this.customer = customer;
        Branch branch = customer.getBranch();
        this.library = branch.getLibrary();
        initComponents();
        refreshTables();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // All books
        JPanel pnlAll = new JPanel(new BorderLayout());
        pnlAll.setBorder(BorderFactory.createTitledBorder("Browse Book Catalog - " + library));

        tblAllBooks = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Serial", "Name", "Author", "Lang", "Rented"}));
        pnlAll.add(new JScrollPane(tblAllBooks), BorderLayout.CENTER);

        JButton btnRent = new JButton("Rent Selected Book");
        btnRent.addActionListener(e -> rentSelected());
        pnlAll.add(btnRent, BorderLayout.SOUTH);

        // my current rentals
        JPanel pnlMy = new JPanel(new BorderLayout());
        pnlMy.setBorder(BorderFactory.createTitledBorder("Return a Book"));

        tblMyRentals = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Record ID", "Book", "Rented At"}));
        pnlMy.add(new JScrollPane(tblMyRentals), BorderLayout.CENTER);

        JButton btnReturn = new JButton("Return Selected Book");
        btnReturn.addActionListener(e -> returnSelected());
        pnlMy.add(btnReturn, BorderLayout.SOUTH);

        // history
        JPanel pnlHistory = new JPanel(new BorderLayout());
        pnlHistory.setBorder(BorderFactory.createTitledBorder("Rental History"));

        tblHistory = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Record ID", "Book", "Branch", "Rented At", "Returned At", "Status"}));
        pnlHistory.add(new JScrollPane(tblHistory), BorderLayout.CENTER);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; gbc.weighty = 0.4;
        center.add(pnlAll, gbc);
        gbc.gridy++;
        gbc.weighty = 0.3;
        center.add(pnlMy, gbc);
        gbc.gridy++;
        gbc.weighty = 0.3;
        center.add(pnlHistory, gbc);
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
        CardLayout cl = (CardLayout) mainContainer.getLayout();
        cl.show(mainContainer, "login");
        });
        add(btnBack, BorderLayout.NORTH);

        add(center, BorderLayout.CENTER);
    }

    private void refreshTables() {
        // all books
        DefaultTableModel allModel = (DefaultTableModel) tblAllBooks.getModel();
        allModel.setRowCount(0);
        for (Book b : library.getBooks()) {
            Object[] row = new Object[5];
            row[0] = b.getSerialNumber();
            row[1] = b.getName();
            row[2] = b.getAuthor() != null ? b.getAuthor().getName() : "";
            row[3] = b.getLanguage();
            row[4] = b.isRented() ? "Currently Rented Out" : "Available";
            allModel.addRow(row);
        }

        // my current rentals
        DefaultTableModel myModel = (DefaultTableModel) tblMyRentals.getModel();
        myModel.setRowCount(0);
        for (RentalRecord r : customer.getRentals()) {
            if (r.getStatus() == RentalStatus.RENTED) {
                Object[] row = new Object[3];
                row[0] = r.getId();
                row[1] = r.getBook().getName();
                row[2] = r.getRentedAt();
                myModel.addRow(row);
            }
        }

        // history
        DefaultTableModel hModel = (DefaultTableModel) tblHistory.getModel();
        hModel.setRowCount(0);
        for (RentalRecord r : customer.getRentals()) {
            Object[] row = new Object[6];
            row[0] = r.getId();
            row[1] = r.getBook().getName();
            row[2] = r.getLibrary().getBranch().getName();
            row[3] = r.getRentedAt();
            row[4] = r.getReturnedAt();
            row[5] = r.getStatus();
            hModel.addRow(row);
        }
    }

    private Book findBookBySerial(int serial) {
        for (Book b : library.getBooks()) {
            if (b.getSerialNumber() == serial) {
                return b;
            }
        }
        return null;
    }

    private RentalRecord findRentalById(int id) {
        for (RentalRecord r : customer.getRentals()) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    private void rentSelected() {
        int row = tblAllBooks.getSelectedRow();
        if (row < 0) {
            return;
        }
        int serial = (Integer) tblAllBooks.getValueAt(row, 0);
        Book b = findBookBySerial(serial);
        if (b == null || b.isRented()) {
            return;
        }
        b.setRented(true);
        RentalRecord record = new RentalRecord(b, customer, library);
        customer.addRental(record);
        system.addRentalRecord(record);
        refreshTables();
    }

    private void returnSelected() {
        int row = tblMyRentals.getSelectedRow();
        if (row < 0) {
            return;
        }
        int id = (Integer) tblMyRentals.getValueAt(row, 0);
        RentalRecord record = findRentalById(id);
        if (record == null || record.getStatus() == RentalStatus.RETURNED) {
            return;
        }
        record.getBook().setRented(false);
        record.markReturned();
        refreshTables();
    }
}
