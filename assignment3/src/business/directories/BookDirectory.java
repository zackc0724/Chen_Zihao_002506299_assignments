package business.directories;

import business.items.Book;
import java.util.ArrayList;
import java.util.List;

public class BookDirectory {
    private final List<Book> list = new ArrayList<>();
    public List<Book> getList() { return list; }
    public Book add(Book b) { list.add(b); return b; }
}
