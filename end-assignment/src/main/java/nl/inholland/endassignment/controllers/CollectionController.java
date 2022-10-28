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
import nl.inholland.endassignment.models.Author;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class CollectionController implements Initializable {

    private final Database db;
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

    public CollectionController(Database db) {
        this.db = db;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.items = FXCollections.observableArrayList(db.getItems());
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
        Author author = new Author(
                this.items.size() + 1L,
                this.firstnameAuthorField.getText(),
                this.prefixField.getText(),
                this.lastnameAuthorField.getText()
        );
        this.items.add(new Item(this.items.get(items.size() - 1).getId() + 1, titleField.getText(), author));
    }

    public void onDeleteItemButtonClick() {
        if(selectedItemId == 0)
            return;

        this.items.remove((int)selectedItemId - 1);
    }

    public void onEditItemButtonClick() {
        if(selectedItemId == 0)
            return;

        this.items.get((int)selectedItemId - 1).setTitle(this.titleField.getText());
        this.items.get((int)selectedItemId - 1).getAuthor().setFirstname(this.firstnameAuthorField.getText());
        this.items.get((int)selectedItemId - 1).getAuthor().setLastnamePrefix(this.prefixField.getText());
        this.items.get((int)selectedItemId - 1).getAuthor().setLastname(this.lastnameAuthorField.getText());

        this.itemsTableView.refresh();
    }

    public void onClearFieldsButtonClick() {
        this.titleField.setText("");
        this.firstnameAuthorField.setText("");
        this.prefixField.setText("");
        this.lastnameAuthorField.setText("");
    }
}
