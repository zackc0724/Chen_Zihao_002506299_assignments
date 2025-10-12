package ui.manager;

import business.EcoSystem;
import business.items.Author;
import business.items.Book;
import business.org.Branch;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.Objects;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ui.common.UI;

public class ManageBooksPanel extends JPanel {
    private final EcoSystem system;
    private final UserAccount account;
    private final JPanel cardPanel;
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"SN","Name","Author","Registered","Available"}, 0) {
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable table = new JTable(model);

    private final JTextField txtName = new JTextField(14);
    private final JSpinner spPages = new JSpinner(new SpinnerNumberModel(300, 1, 5000, 10));
    private final JComboBox<Author> cmbAuthor;
    private final JTextField txtLang = new JTextField(10);
    private Branch myBranch;

    public ManageBooksPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.system = system;
        this.account = account;
        this.cardPanel = cardPanel;
        setLayout(new BorderLayout(6,6));

        myBranch = system.getBranchDirectory().getList().stream()
                .filter(b -> b.getBranchManager()!=null && account.getEmployee()!=null
                        && Objects.equals(b.getBranchManager().getEmployeeId(), account.getEmployee().getEmployeeId()))
                .findFirst().orElse(null);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        header.add(new JLabel("Name:")); header.add(txtName);
        header.add(new JLabel("Pages:")); header.add(spPages);

        cmbAuthor = new JComboBox<>(system.getAuthorDirectory().getList().toArray(new Author[0]));
        header.add(new JLabel("Author:")); header.add(cmbAuthor);

        JButton btnAddAuthorQuick = new JButton("+");
        btnAddAuthorQuick.setToolTipText("Quick-add a new author");
        btnAddAuthorQuick.addActionListener(e -> quickAddAuthor());
        header.add(btnAddAuthorQuick);

        header.add(new JLabel("Language:")); header.add(txtLang);
        add(header, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Add Book");
        JPanel southControls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southControls.add(btnAdd);
        add(southControls, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> doAddBook());
        refresh();
        UI.addLogoutBar(this, cardPanel, system);
    }

    private void quickAddAuthor() {
        String name = JOptionPane.showInputDialog(this, "Author name:");
        if (name == null) return;
        name = name.trim();
        if (name.isEmpty()) return;
        Author a = new Author(name);
        system.getAuthorDirectory().add(a);
        cmbAuthor.addItem(a);
        cmbAuthor.setSelectedItem(a);
    }

    private void doAddBook() {
        if (myBranch == null) {
            JOptionPane.showMessageDialog(this, "Manager not assigned to a branch yet.");
            return;
        }
        String name = txtName.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter a book name"); return; }
        Author a = (Author) cmbAuthor.getSelectedItem();
        int pages = (int) spPages.getValue();
        String lang = txtLang.getText().trim();

        Book b = new Book(name, LocalDate.now(), pages, lang, a, myBranch);
        system.getBookDirectory().add(b);

        txtName.setText(""); txtLang.setText(""); spPages.setValue(300);
        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        system.getBookDirectory().getList().stream()
                .filter(b -> myBranch == null || (b.getOwnerBranch()!=null && b.getOwnerBranch().equals(myBranch)))
                .forEach(b -> model.addRow(new Object[]{
                        b.getSerialNumber(), b.getName(),
                        b.getAuthor()!=null?b.getAuthor().getName():"",
                        b.getRegisteredDate(), b.isAvailable() ? "true" : "false"
                }));
    }
}
