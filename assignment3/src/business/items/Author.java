package business.items;

public class Author {
    private static int COUNTER = 1;
    private final int authorId;
    private String name;

    public Author(String name) {
        this.authorId = COUNTER++;
        this.name = name;
    }
    public int getAuthorId() { return authorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @Override public String toString() { return name + " (#" + authorId + ")"; }
}
