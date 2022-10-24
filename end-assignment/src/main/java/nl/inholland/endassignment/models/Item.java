package nl.inholland.endassignment.models;

public class Item {
    private long id;
    private AvailabilityType available = AvailabilityType.YES;
    private String title;
    private Author author;

    public Item(long id, String title, Author author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AvailabilityType getAvailable() {
        return available;
    }

    public void setAvailable(AvailabilityType available) {
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getAuthorName() {
        return this.author.getLastname() + ", " + this.author.getFirstname().charAt(0) + "." + this.author.getLastnamePrefix();
    }
}
