package business.directories;

import business.items.Author;
import java.util.ArrayList;
import java.util.List;

public class AuthorDirectory {
    private final List<Author> list = new ArrayList<>();
    public List<Author> getList() { return list; }
    public Author add(Author a) { list.add(a); return a; }
}
