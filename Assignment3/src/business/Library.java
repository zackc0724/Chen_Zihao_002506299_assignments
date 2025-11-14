package business;

import java.util.ArrayList;
import java.util.List;

/**
 * Library holds books and is associated with exactly one Branch.
 */
public class Library {

    private String buildingNo;
    private Branch branch;
    private List<Book> books = new ArrayList<>();

    public Library(String buildingNo, Branch branch) {
        this.buildingNo = buildingNo;
        this.branch = branch;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public Branch getBranch() {
        return branch;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book b) {
        books.add(b);
    }

    @Override
    public String toString() {
        return "Library " + buildingNo + " (" + branch.getName() + ")";
    }
}
