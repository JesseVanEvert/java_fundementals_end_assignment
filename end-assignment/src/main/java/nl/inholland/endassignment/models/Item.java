package nl.inholland.endassignment.models;

import java.time.LocalDate;

public class Item {
    private long id;
    private LocalDate lendOutOn = null;
    private String title;
    private Author author;

    public Item(long id, String title, Author author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Item(long id, LocalDate lendOutOn, String title, Author author) {
        this.id = id;
        this.lendOutOn = lendOutOn;
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
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getAuthorName() {
        return author.getAuthorName();
    }

    public String getAvailable() {
        return ((this.lendOutOn == null) ? "Yes" : "No");
    }
}
