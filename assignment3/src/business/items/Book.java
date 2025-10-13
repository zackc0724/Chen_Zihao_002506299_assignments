package business.items;

import business.org.Branch;
import java.time.LocalDate;

public class Book {
    private static int SERIAL_COUNTER = 1;
    private final int serialNumber = SERIAL_COUNTER++;
    private String name;
    private LocalDate registeredDate;
    private int pages;
    private String language;
    private boolean isRented;
    private Author author;
    private Branch ownerBranch;

    public Book(String name, LocalDate registeredDate, int pages, String language, Author author, Branch ownerBranch) {
        this.name = name;
        this.registeredDate = registeredDate;
        this.pages = pages;
        this.language = language;
        this.author = author;
        this.ownerBranch = ownerBranch;
    }
    public int getSerialNumber() { return serialNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getRegisteredDate() { return registeredDate; }
    public void setRegisteredDate(LocalDate d) { this.registeredDate = d; }
    public int getPages() { return pages; }
    public void setPages(int p) { this.pages = p; }
    public String getLanguage() { return language; }
    public void setLanguage(String l) { this.language = l; }
    public boolean isRented() { return isRented; }
    public void markRented() { this.isRented = true; }
    public void markReturned() { this.isRented = false; }
    public Author getAuthor() { return author; }
    public void setAuthor(Author a) { this.author = a; }
    public Branch getOwnerBranch() { return ownerBranch; }
    public void setOwnerBranch(Branch b) { this.ownerBranch = b; }
    public boolean isAvailable() { return !isRented; }
    @Override public String toString() { return name + " (SN " + serialNumber + ")"; }
}
