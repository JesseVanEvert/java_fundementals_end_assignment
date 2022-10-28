package nl.inholland.endassignment.controllers;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class CollectionController implements Initializable {

    private final Database db;
    private final User loggedInUser;
    private ObservableList<Item> items;
    private long selectedItemId;
    @FXML
    private TableView<Item> itemsTableView;
    @FXML
    private TextField titleField;
    @FXML
    private TextField firstnameAuthorField;
    @FXML
    private TextField prefixField;
    @FXML
    private TextField lastnameAuthorField;

    public CollectionController(Database db, User loggedInUser) {
        this.db = db;
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.items = FXCollections.observableArrayList(db.getItems());
        //this.itemsTableView.setItems(items);
        Property<ObservableList<Item>> authorListProperty = new SimpleObjectProperty<>(items);
        this.itemsTableView.itemsProperty().bind(authorListProperty);
        this.itemsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.addTableViewListener();
    }

    private void addTableViewListener() {
        itemsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            this.selectedItemId = newSelection.getId();
            this.titleField.setText(newSelection.getTitle());
            this.firstnameAuthorField.setText(newSelection.getAuthor().getFirstname());
            this.prefixField.setText(newSelection.getAuthor().getLastnamePrefix());
            this.lastnameAuthorField.setText(newSelection.getAuthor().getLastname());
        });
    }
    public void onAddItemButtonClick() {

    }

    public void onDeleteItemButtonClick() {

    }

    public void onEditItemButtonClick() {
        if(selectedItemId == 0)
            return;

        items.get((int)selectedItemId - 1).setTitle(this.titleField.getText());
        items.get((int)selectedItemId - 1).getAuthor().setFirstname(this.firstnameAuthorField.getText());
        items.get((int)selectedItemId - 1).getAuthor().setLastnamePrefix(this.prefixField.getText());
        items.get((int)selectedItemId - 1).getAuthor().setLastname(this.lastnameAuthorField.getText());

        itemsTableView.refresh();
    }

    public void onClearFieldsButtonClick() {

    }
}
