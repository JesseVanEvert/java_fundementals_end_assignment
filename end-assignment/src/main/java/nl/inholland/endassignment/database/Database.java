package nl.inholland.endassignment.database;

import nl.inholland.endassignment.models.Author;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;
import nl.inholland.endassignment.models.UserType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();

    public Database() {
        //Regular user
        this.users.add(new User(users.size(), "kevin@hotmail.nl", "Kevin", "de", "vries", "wachtwoord", LocalDate.ofYearDay(1999, 3)));
        //Admin user
        this.users.add(new User(users.size(), "Peter", "peter@outlook.com", "", "Janssen", "wachtwoord", LocalDate.ofYearDay(1999, 3), UserType.ADMIN));
        Author dostojevski = new Author(items.size(), "Fyodor", "", "Dostojevski");
        Author dickens = new Author(items.size(), "Charles", "", "Dickens");
        this.items.add(new Item(items.size(), "Crime and punishment", dostojevski));
        Item aTaleOfTwoCities = new Item(items.size(), "A tale of two cities", dickens);
        aTaleOfTwoCities.setLendOutOn(LocalDate.ofYearDay(2022, 2));
        this.items.add(aTaleOfTwoCities);

    }

    public List<User> getUsers() {
        return users;
    }

    public List<Item> getItems() {
        return items;
    }
}
