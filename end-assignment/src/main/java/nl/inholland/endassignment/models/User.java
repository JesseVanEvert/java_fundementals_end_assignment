package nl.inholland.endassignment.models;

import java.time.LocalDate;

public class User {
    private long id;
    private String email;
    private String firstname;
    private String lastnamePrefix;
    private String lastname;
    private String password;
    private LocalDate dateOfBirth;
    private UserType userType = UserType.REGULAR;



    public User(long id, String email, String firstname, String lastnamePrefix, String lastname, String password, LocalDate dateOfBirth) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastnamePrefix = lastnamePrefix;
        this.lastname = lastname;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public User(long id, String email, String firstname, String lastnamePrefix, String lastname, String password, LocalDate dateOfBirth, UserType userType) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastnamePrefix = lastnamePrefix;
        this.lastname = lastname;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType;
    }

    public User() {}

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getLastnamePrefix() {
        return lastnamePrefix;
    }

    public void setLastnamePrefix(String lastnamePrefix) {
        this.lastnamePrefix = lastnamePrefix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
