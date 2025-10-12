package ui.manager;

import business.EcoSystem;
import business.items.Author;
import business.items.Book;
import business.org.Branch;
import business.useraccount.UserAccount;
import java.awt.BorderLayout;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageBooksPanel extends JPanel {
    private final EcoSystem system;
    private final UserAccount account;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"SN","Name","Author","Branch","Available"},0);
    private final JTable table = new JTable(model);

    private final JTextField txtName = new JTextField(12);
    private final JSpinner spPages = new JSpinner(new SpinnerNumberModel(100,1,2000,10));
    private final JTextField txtLang = new JTextField(8);
    private final JComboBox<Author> cmbAuthor;
    private final JComboBox<Branch> cmbBranch;

    public ManageBooksPanel(JPanel cardPanel, EcoSystem system, UserAccount account) {
        this.system = system;
        this.account = account;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        cmbAuthor = new JComboBox<>(system.getAuthorDirectory().getList().toArray(new Author[0]));
        cmbBranch = new JComboBox<>(system.getBranchDirectory().getList().toArray(new Branch[0]));

        JPanel south = new JPanel();
        south.add(new JLabel("Name:")); south.add(txtName);
        south.add(new JLabel("Pages:")); south.add(spPages);
        south.add(new JLabel("Lang:")); south.add(txtLang);
        south.add(new JLabel("Author:")); south.add(cmbAuthor);
        south.add(new JLabel("Branch:")); south.add(cmbBranch);
        JButton btnAdd = new JButton("Add");
        south.add(btnAdd);
        add(south, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            String name = txtName.getText().trim();
            if(name.isEmpty()) return;
            Author a = (Author) cmbAuthor.getSelectedItem();
            Branch b = (Branch) cmbBranch.getSelectedItem();
            Book book = new Book(name, LocalDate.now(), (int)spPages.getValue(), txtLang.getText().trim(), a, b);
            system.getBookDirectory().add(book);
            refresh();
        });

        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        for (Book b : system.getBookDirectory().getList()) {
            model.addRow(new Object[]{ b.getSerialNumber(), b.getName(),
                b.getAuthor()!=null?b.getAuthor().getName():"", 
                b.getOwnerBranch()!=null?b.getOwnerBranch().getName():"",
                b.isAvailable()? "Available":"Rented" });
        }
    }
}
