package nl.inholland.endassignment.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final Database database;
    private User loggedInUser;
    @FXML
    private Text welcomeText;

    public MainViewController(Database database, User loggedInUser) {
        this.database = database;
        this.loggedInUser = loggedInUser;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.welcomeText.setText("Welcome " + loggedInUser.getFirstname());
    }
}
