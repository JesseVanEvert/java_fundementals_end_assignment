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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private long selectedItemId;

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

    public void onAddUserButtonClick() {
        User user = new User(
                db.getUsers().get(db.getUsers().size() - 1).getId() + 1,
                this.emailField.getText(),
                this.firstnameUserField.getText(),
                this.prefixField.getText(),
                this.lastnameUserField.getText(),
                this.passwordField.getText(),
                this.birthdayDatePicker.getValue()
        );

        db.getUsers().add(user);
        this.writeUsersToCsv();
    }

    public void onEditUserButtonClick() {
        if(selectedItemId == 0)
            return;

        db.getUsers().get((int)selectedItemId - 1).setEmail(this.emailField.getText());
        db.getUsers().get((int)selectedItemId - 1).setFirstname(this.firstnameUserField.getText());
        db.getUsers().get((int)selectedItemId - 1).setLastnamePrefix(this.prefixField.getText());
        db.getUsers().get((int)selectedItemId - 1).setLastname(this.lastnameUserField.getText());
        db.getUsers().get((int)selectedItemId - 1).setPassword(this.passwordField.getText());
        db.getUsers().get((int)selectedItemId - 1).setDateOfBirth(this.birthdayDatePicker.getValue());

        this.usersTableView.refresh();
        this.writeUsersToCsv();
    }

    public void onDeleteUserButtonClick() {
        if(selectedItemId == 0)
            return;

        db.getUsers().remove((int)selectedItemId - 1);
        this.writeUsersToCsv();
    }

    private void writeUsersToCsv() {
        try {
            db.writeUsersToCsv();
        } catch (IOException ex) {
            System.out.println("werkt niet");
        }
    }

    public void onClearFieldsButtonClick(){
        this.emailField.setText("");
        this.firstnameUserField.setText("");
        this.prefixField.setText("");
        this.lastnameUserField.setText("");
        this.birthdayDatePicker.setValue(null);
    }
}
