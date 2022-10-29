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

import java.net.URL;
import java.util.ResourceBundle;

public class MembersController implements Initializable {
    private final Database db;
    private ObservableList<User> users;
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
    private long selectedItemId;

    public MembersController(Database db) {
        this.db = db;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.users = FXCollections.observableArrayList(db.getUsers());
        Property<ObservableList<User>> memberListProperty = new SimpleObjectProperty<>(users);
        this.usersTableView.itemsProperty().bind(memberListProperty);
        this.usersTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.addTableViewListener();
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

    public void onAddUserButtonClick() {
        User user = new User(
                this.users.get(users.size() - 1).getId() + 1,
                this.emailField.getText(),
                this.firstnameUserField.getText(),
                this.prefixField.getText(),
                this.lastnameUserField.getText(),
                this.passwordField.getText(),
                this.birthdayDatePicker.getValue()
        );

        this.users.add(user);
    }

    public void onEditUserButtonClick() {
        if(selectedItemId == 0)
            return;

        this.users.get((int)selectedItemId - 1).setEmail(this.emailField.getText());
        this.users.get((int)selectedItemId - 1).setFirstname(this.firstnameUserField.getText());
        this.users.get((int)selectedItemId - 1).setLastnamePrefix(this.prefixField.getText());
        this.users.get((int)selectedItemId - 1).setLastname(this.lastnameUserField.getText());
        this.users.get((int)selectedItemId - 1).setPassword(this.passwordField.getText());
        this.users.get((int)selectedItemId - 1).setDateOfBirth(this.birthdayDatePicker.getValue());

        this.usersTableView.refresh();
    }

    public void onDeleteUserButtonClick() {
        if(selectedItemId == 0)
            return;

        this.users.remove((int)selectedItemId - 1);
    }

    public void onClearFieldsButtonClick(){
        this.emailField.setText("");
        this.firstnameUserField.setText("");
        this.prefixField.setText("");
        this.lastnameUserField.setText("");
        this.birthdayDatePicker.setValue(null);
    }
}
