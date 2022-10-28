package nl.inholland.endassignment.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Item {
    private long id;
    private LocalDate lendOutOn = null;
    private String title;
    private String available = "Yes";
    private Author author;
    private String authorName;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public LocalDate getLendOutOn() {
        return lendOutOn;
    }

    public void setLendOutOn(LocalDate lendOutOn) {
        this.lendOutOn = lendOutOn;
        this.available = ((lendOutOn == null) ? "Yes" : "No");
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getAuthorName() {
        return author.getAuthorName();
    }

    public String getAvailable() {
        return available;
    }
}
