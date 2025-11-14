package business;

/**
 * Author entity. The system maintains all authors.
 */
public class Author {

    private static int nextId = 1;

    private int id;
    private String name;

    public Author(String name) {
        this.id = nextId++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
