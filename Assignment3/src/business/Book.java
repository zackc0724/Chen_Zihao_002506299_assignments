package business;

import java.time.LocalDate;

/**
 * Book in a Library.
 */
public class Book {

    private static int nextSerial = 1;

    private int serialNumber;
    private String name;
    private LocalDate registeredDate;
    private int pages;
    private String language;
    private boolean isRented;
    private Author author;

    public Book(String name, Author author, int pages, String language) {
        this.serialNumber = nextSerial++;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.language = language;
        this.registeredDate = LocalDate.now();
        this.isRented = false;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public int getPages() {
        return pages;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return name + " (#" + serialNumber + ")";
    }
}
