package nl.inholland.endassignment.controllers;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.endassignment.LibraryApplication;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    private final Database db;
    @FXML
    private GridPane gridpane;
    private final Logger log = Logger.getLogger(this.getClass().getName());
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginFeedbackLabel;

    private User loggedInUser;

    public LoginController() {
        db = new Database();
    }


    @FXML
    public void onLoginButtonClick(){
        if(isLoginValid()) {
            log.log(Level.INFO, "User with email: {0} has logged in", emailField.getText());
            loadScene("main-view.fxml", new MainViewController(db, loggedInUser));
        } else {
            loginFeedbackLabel.setText("One or more fields\nare filled in incorrectly");
            log.log(Level.INFO, "User with email: {0} has attempted to log in, but has been denied access", emailField.getText());
        }
    }

    private boolean isLoginValid() {
        String emailInput = emailField.getText().strip();
        String passwordInput = passwordField.getText().strip();

        if(emailInput.equals("") || passwordInput.equals("")){
            return false;
        }

        for (User user : db.getUsers()) {
            if(user.getEmail().equals(emailInput) && user.getPassword().equals(passwordInput)) {
                loggedInUser = user;
                return true;
            }
        }

        return false;
    }

    public void loadScene(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage window = (Stage) gridpane.getScene().getWindow();
            window.setTitle(name.replace(".fxml", ""));
            window.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}