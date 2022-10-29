package nl.inholland.endassignment.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.inholland.endassignment.models.Author;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final ObservableList<Item> items = FXCollections.observableArrayList();

    public Database() {
        this.users.add(new User(users.size() + 1L, "kevin@hotmail.nl", "Kevin", "de", "vries", "wachtwoord", LocalDate.ofYearDay(1999, 3)));
        this.users.add(new User(users.size() + 1L, "peter@outlook.com","Peter",  "", "Janssen", "wachtwoord", LocalDate.ofYearDay(1999, 3)));

        Author dostoyevsky = new Author(items.size() + 1L, "Fyodor", "", "Dostoyevsky");
        Author dickens = new Author(items.size() + 1L, "Charles", "", "Dickens");
        Author marquez = new Author(items.size() + 1L, "Gabriel", "Garcia", "Marquez");

        this.items.add(new Item(items.size() + 1L, "Crime and punishment", dostoyevsky));
        Item aTaleOfTwoCities = new Item(items.size() + 1L, "A tale of two cities", dickens);
        aTaleOfTwoCities.setLendOutOn(LocalDate.ofYearDay(2022, 2));

        this.items.add(aTaleOfTwoCities);
        this.items.add(new Item(items.size() + 1L, "One Hundred Years of Solitude", marquez));

        try {
            this.writeItemsToCsv();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void writeItemsToCsv() throws IOException {
        Writer userWriter = null;
        Writer authorWriter = null;
        try {
            File itemsFile = new File("items.csv");
            userWriter = new BufferedWriter(new FileWriter(itemsFile, false));
            File authorsFile = new File("authors.csv");
            authorWriter = new BufferedWriter(new FileWriter(authorsFile, false));

            for (Item item : this.items) {
                String itemRow = item.getId() + "," + item.getLendOutOn() + "," + item.getTitle() + "," + "\n";
                userWriter.write(itemRow);
                Author author = item.getAuthor();
                String authorRow = author.getId() + "," + author.getFirstname() + "," + author.getLastnamePrefix() + "," + author.getLastname() + "\n";
                authorWriter.write(authorRow);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            userWriter.flush();
            userWriter.close();
            authorWriter.flush();
            authorWriter.close();
        }
    }

    /*private void addItemsOnListChange() {
        items.addListener((ListChangeListener.Change<? extends Item> item ) -> {
            while (item.next()) {
                if (item.wasPermutated()) {
                    for (int i = item.getFrom(); i < item.getTo(); ++i) {
                        //permutate
                    }
                } else if (item.wasUpdated()) {
                    //update item
                } else {
                    for (Item remItem : item.getRemoved()) {
                        //remitem.remove(Outer.this);
                    }
                    for (Item addItem : item.getAddedSubList()) {
                        try {
                            this.addItemToCsvFile(addItem);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    private void addItemToCsvFile(Item item) throws Exception {
        Writer userWriter = null;
        Writer authorWriter = null;
        try {
            File itemsFile = new File("items.csv");
            userWriter = new BufferedWriter(new FileWriter(itemsFile));
            File authorsFile = new File("authors.csv");
            authorWriter = new BufferedWriter(new FileWriter(authorsFile));

            String itemRow = item.getId() + "," + item.getLendOutOn() + "," + item.getTitle() + "," + "\n";
            Author author = item.getAuthor();
            String authorRow = author.getId() + "," + author.getFirstname() + "," + author.getLastnamePrefix() + "," + author.getLastname() + "\n";

            userWriter.write(itemRow);
            authorWriter.write(authorRow);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            userWriter.flush();
            userWriter.close();
            authorWriter.flush();
            authorWriter.close();
        }
    }*/


    public ObservableList<User> getUsers() {
        return users;
    }

    public ObservableList<Item> getItems() {
        return items;
    }
}
