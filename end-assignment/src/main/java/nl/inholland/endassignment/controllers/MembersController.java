package nl.inholland.endassignment.controllers;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MembersController implements Initializable {
    private final Database db;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TextField emailField;
    @FXML
    private TextField firstnameUserField;
    @FXML
    private TextField prefixField;
    @FXML
    private TextField lastnameUserField;
    @FXML
    private DatePicker birthdayDatePicker;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField searchMembersField;
    @FXML
    private Label feedbackMembersLabel;
    private long selectedItemId;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public MembersController(Database db) {
        this.db = db;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usersTableView.setItems(db.getUsers());
        this.usersTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.addTableViewListener();
        this.addSearchFieldListener();
    }

    @FXML
    private void onAddUserButtonClick() {
        User user = new User(
                db.getUsers().get(db.getUsers().size() - 1).getId() + 1,
                this.emailField.getText(),
                this.firstnameUserField.getText(),
                this.prefixField.getText(),
                this.lastnameUserField.getText(),
                this.passwordField.getText(),
                birthdayDatePicker.getConverter().fromString(birthdayDatePicker.getEditor().getText())
        );

        if(!this.areRequiredFieldFilledIn())
            return;

        if(!this.isPasswordValid())
            return;

        if(!this.isUserEmailUnique())
            return;

        db.getUsers().add(user);
        this.feedbackMembersLabel.setText("Added user: " + user.getFirstname());
        this.feedbackMembersLabel.setVisible(true);
        this.usersTableView.refresh();
        this.writeUsersToCsv();
    }

    @FXML
    private void onClearFieldsButtonClick(){
        this.emailField.setText("");
        this.firstnameUserField.setText("");
        this.prefixField.setText("");
        this.lastnameUserField.setText("");
        this.passwordField.setText("");
        this.birthdayDatePicker.setValue(null);
    }

    @FXML
    private void onEditUserButtonClick() {
        if(selectedItemId == 0)
            return;

        if(!this.areRequiredFieldFilledIn())
            return;

        if(!this.isPasswordValid())
            return;

        if(!this.isUserEmailUnique())
            return;

        db.getUsers().get((int)selectedItemId - 1).setEmail(this.emailField.getText());
        db.getUsers().get((int)selectedItemId - 1).setFirstname(this.firstnameUserField.getText());
        db.getUsers().get((int)selectedItemId - 1).setLastnamePrefix(this.prefixField.getText());
        db.getUsers().get((int)selectedItemId - 1).setLastname(this.lastnameUserField.getText());
        db.getUsers().get((int)selectedItemId - 1).setPassword(this.passwordField.getText());
        db.getUsers().get((int)selectedItemId - 1).setDateOfBirth(this.birthdayDatePicker.getValue());

        this.feedbackMembersLabel.setText("Edited user: " + this.firstnameUserField.getText());
        this.feedbackMembersLabel.setVisible(true);
        this.usersTableView.refresh();
        this.writeUsersToCsv();
    }

    @FXML
    private void onDeleteUserButtonClick() {
        if(selectedItemId == 0)
            return;

        if(isLastUserBeingDeleted())
            return;

        db.getUsers().remove((int)selectedItemId - 1);
        this.feedbackMembersLabel.setText("Deleted user: " + this.firstnameUserField.getText());
        this.feedbackMembersLabel.setVisible(true);
        this.usersTableView.setItems(db.getUsers());
        this.writeUsersToCsv();
    }

    private boolean isUserEmailUnique() {
        for (User user : db.getUsers()) {
            if(this.emailField.getText().equals(db.getUsers().get((int)selectedItemId - 1).getEmail()))
                return true;
            if(user.getEmail().equals(this.emailField.getText())) {
                this.feedbackMembersLabel.setText("Email is already registered");
                this.feedbackMembersLabel.setVisible(true);
                return false;
            }
        }

        return true;
    }

    private boolean isPasswordValid() {
        String password = this.passwordField.getText().strip();

        boolean hasLetters = false;
        boolean hasDigits = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigits = true;
            } else if (Character.isLetter(c)) {
                hasLetters = true;
            } else {
                hasSpecial = true;
            }
        }
        if(password.length() > 7 && (hasLetters && hasDigits && hasSpecial))
            return true;

        this.feedbackMembersLabel.setText("Use letters, digits and special characters in password totaling > 7 characters");
        this.feedbackMembersLabel.setVisible(true);
        return false;
    }

    private boolean isLastUserBeingDeleted() {
        if(db.getUsers().size() == 1) {
            this.feedbackMembersLabel.setText("You can't delete every user");
            this.feedbackMembersLabel.setVisible(true);
            return true;
        }

        return false;
    }

    private void writeUsersToCsv() {
        try {
            db.writeUsersToCsv();
        } catch (IOException ex) {
            this.log.warning("Writing to users csv failed at: " + LocalDate.now() + " exception: " + ex);
        }
    }

    private void addTableViewListener() {
        usersTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            this.selectedItemId = newSelection.getId();
            this.emailField.setText(newSelection.getEmail());
            this.firstnameUserField.setText(newSelection.getFirstname());
            this.firstnameUserField.setText(newSelection.getFirstname());
            this.prefixField.setText(newSelection.getLastnamePrefix());
            this.lastnameUserField.setText(newSelection.getLastname());
            this.birthdayDatePicker.setValue(newSelection.getDateOfBirth());
        });
    }

    private boolean searchUser(User user, String searchText){
        return (user.getFirstname().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getLastname().toLowerCase().contains(searchText.toLowerCase()));
    }

    private ObservableList<User> filterList(List<User> list, String searchText){
        List<User> filteredList = new ArrayList<>();
        for (User user : list){
            if(searchUser(user, searchText)) filteredList.add(user);
        }
        return FXCollections.observableList(filteredList);
    }

    private void addSearchFieldListener() {
        searchMembersField.textProperty().addListener((observable, oldValue, newValue) ->
                this.usersTableView.setItems(filterList(this.db.getUsers(), newValue))
        );
    }

    private boolean areRequiredFieldFilledIn() {
        if(
                this.emailField.getText().strip().equals("") ||
                        this.firstnameUserField.getText().strip().equals("") ||
                        this.lastnameUserField.getText().equals("") ||
                        this.passwordField.getText().strip().equals("") ||
                        this.birthdayDatePicker.getValue() == null
        ) {
            this.feedbackMembersLabel.setText("All fields except for prefix have to be filled");
            this.feedbackMembersLabel.setVisible(true);
            return false;
        }

        return true;
    }
}
