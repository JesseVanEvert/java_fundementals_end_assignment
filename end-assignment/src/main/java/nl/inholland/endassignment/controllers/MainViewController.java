package nl.inholland.endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainViewController {

    protected final Database db;
    protected User loggedInUser;
    protected ObservableList<Item> items;
    protected ObservableList<User> users;


    public MainViewController(Database db, User loggedInUser) {
        this.db = db;
        this.loggedInUser = loggedInUser;
    }

    /*@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this.welcomeText.setText("Welcome " + loggedInUser.getFirstname());
        //this.memberIdTextField.setText(Long.toString(this.loggedInUser.getId()));
        this.items = FXCollections.observableArrayList(db.getItems());
        this.users = FXCollections.observableArrayList(db.getUsers());
    }*/
}
