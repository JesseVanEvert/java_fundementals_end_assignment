package nl.inholland.endassignment.models;

public class Author {
    private long id;
    private String firstname;
    private String lastnamePrefix;
    private String lastname;

    public Author(long id, String firstname, String lastnamePrefix, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastnamePrefix = lastnamePrefix;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastnamePrefix() {
        return lastnamePrefix;
    }

    public void setLastnamePrefix(String lastnamePrefix) {
        this.lastnamePrefix = lastnamePrefix;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
