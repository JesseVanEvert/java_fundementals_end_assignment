package nl.inholland.endassignment.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.inholland.endassignment.models.Author;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final ObservableList<Item> items = FXCollections.observableArrayList();

    public Database() {
        this.users.add(new User(users.size() + 1L, "hans@hotmail.nl", "Hans", "de", "vries", "wachtwoord", LocalDate.ofYearDay(1999, 3)));
        this.users.add(new User(users.size() + 1L, "henk@outlook.com","Henk",  "", "Janssen", "wachtwoord", LocalDate.ofYearDay(1999, 3)));

        Author dostoyevsky = new Author(items.size() + 1L, "Fyodor", "", "Dostoyevsky");
        Author dickens = new Author(items.size() + 1L, "Charles", "", "Dickens");
        Author marquez = new Author(items.size() + 1L, "Gabriel", "Garcia", "Marquez");

        this.items.add(new Item(items.size() + 1L, "Crime and punishment", dostoyevsky));
        Item aTaleOfTwoCities = new Item(items.size() + 1L, "A tale of two cities", dickens);
        aTaleOfTwoCities.setLendOutOn(LocalDate.ofYearDay(2022, 2));

        this.items.add(aTaleOfTwoCities);
        this.items.add(new Item(items.size() + 1L, "One Hundred Years of Solitude", marquez));

        this.readItemsFromCsvToList();
        this.readUsersFromCsvToList();
    }

    public void readUsersFromCsvToList() {
        try {
            Files.readAllLines(Paths.get("users.csv"))
                    .forEach(line ->
                            users.add(
                                    new User(
                                            Long.parseLong(line.split(",")[0]),
                                            line.split(",")[1],
                                            line.split(",")[2],
                                            line.split(",")[3],
                                            line.split(",")[4],
                                            line.split(",")[5],
                                            LocalDate.parse(line.split(",")[6])
                                    )
                            )
                    );
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception ex) {

        }
    }
    public void readItemsFromCsvToList() {
        List<Author> authors = this.getAuthorsFromCsv();

        try {
            Files.readAllLines(Paths.get("items.csv"))
                    .forEach(line ->
                            items.add(
                                    new Item(
                                            Long.parseLong(line.split(",")[0]),
                                            (line.split(",")[1] == null) ? (LocalDate.parse(line.split(",")[1])) : null,
                                            line.split(",")[2],
                                            authors.get((int)Long.parseLong(line.split(",")[0]) - 1)
                                    )
                            )
                    );
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception ex) {

        }
    }

    public List<Author> getAuthorsFromCsv() {
        List<Author> authors = new ArrayList<>();
        try {
            Files.readAllLines(Paths.get("authors.csv"))
                    .forEach(line ->
                            authors.add(
                                    new Author(
                                            Long.parseLong(line.split(",")[0]),
                                            line.split(",")[1],
                                            line.split(",")[2],
                                            line.split(",")[3]
                                    )
                            )
                    );
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception ex) {

        }

        return authors;
    }

    public void writeUsersToCsv() throws IOException {
        Writer userWriter = null;

        try {
            File itemsFile = new File("users.csv");
            userWriter = new BufferedWriter(new FileWriter(itemsFile, false));

            for (User user: this.users) {
                String itemRow = user.getId() + "," + user.getEmail() + "," + user.getFirstname() + "," +
                        user.getLastnamePrefix() + "," + user.getLastname() + "," + user.getPassword() + "," +
                        user.getDateOfBirth() + "\n";
                userWriter.write(itemRow);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            userWriter.flush();
            userWriter.close();
        }
    }
    public void writeItemsToCsv() throws IOException {
        Writer itemWriter = null;
        Writer authorWriter = null;
        try {
            File itemsFile = new File("items.csv");
            itemWriter = new BufferedWriter(new FileWriter(itemsFile, false));
            File authorsFile = new File("authors.csv");
            authorWriter = new BufferedWriter(new FileWriter(authorsFile, false));

            for (Item item : this.items) {
                String itemRow = item.getId() + "," + item.getLendOutOn() + "," + item.getTitle() + "," + "\n";
                itemWriter.write(itemRow);
                Author author = item.getAuthor();
                String authorRow = author.getId() + "," + author.getFirstname() + "," + author.getLastnamePrefix() + "," + author.getLastname() + "\n";
                authorWriter.write(authorRow);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            itemWriter.flush();
            itemWriter.close();
            authorWriter.flush();
            authorWriter.close();
        }
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public ObservableList<Item> getItems() {
        return items;
    }
}
