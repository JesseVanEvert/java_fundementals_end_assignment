package nl.inholland.endassignment.database;

import nl.inholland.endassignment.models.Author;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;
import nl.inholland.endassignment.models.UserType;

import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    public Database() {
        //Regular user
        this.users.add(new User(users.size() + 1, "Kevin", "de", "vries", "p@$$w0rD123", LocalDate.ofYearDay(1999, 3)));
        //Admin user
        this.users.add(new User(users.size() + 1, "Peter", "", "Janssen", "p@$$w0rD123", LocalDate.ofYearDay(1999, 3), UserType.ADMIN));
        Author dostojevski = new Author(items.size() + 1, "Fyodor", "", "Dostojevski");
        Author dickens = new Author(items.size() + 1, "Charles", "", "Dickens");
        this.items.add(new Item(items.size() + 1, "Crime and punishment", dostojevski));
        this.items.add(new Item(items.size() + 1, "A tale of two cities", dickens));
    }
}
