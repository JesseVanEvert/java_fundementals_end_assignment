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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CollectionController implements Initializable {

    private final Database db;
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
    @FXML
    private TextField searchItemsField;

    public CollectionController(Database db) {
        this.db = db;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.itemsTableView.setItems(db.getItems());
        this.itemsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.addTableViewListener();
        this.addSearchFieldListener();
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

    private boolean searchItem(Item item, String searchText){
        return (item.getTitle().toLowerCase().contains(searchText.toLowerCase())) ||
                item.getAuthor().getFirstname().toLowerCase().contains(searchText.toLowerCase()) ||
                item.getAuthor().getLastname().toLowerCase().contains(searchText.toLowerCase()) ||
                item.getAuthor().getAuthorName().toLowerCase().contains(searchText.toLowerCase());
    }

    private ObservableList<Item> filterList(List<Item> list, String searchText){
        List<Item> filteredList = new ArrayList<>();
        for (Item item : list){
            if(searchItem(item, searchText)) filteredList.add(item);
        }
        return FXCollections.observableList(filteredList);
    }

    private void addSearchFieldListener() {
        searchItemsField.textProperty().addListener((observable, oldValue, newValue) ->
                this.itemsTableView.setItems(filterList(this.db.getItems(), newValue))
        );
    }

    public void onAddItemButtonClick() {
        Author author = new Author(
                db.getItems().size() + 1L,
                this.firstnameAuthorField.getText(),
                this.prefixField.getText(),
                this.lastnameAuthorField.getText()
        );

        db.getItems().add(new Item(db.getItems().get(db.getItems().size() - 1).getId() + 1, titleField.getText(), author));
        this.writeItemsToCsv();
    }

    public void onDeleteItemButtonClick() {
        if(selectedItemId == 0)
            return;

        db.getItems().remove((int)selectedItemId - 1);
        this.writeItemsToCsv();
    }

    private void writeItemsToCsv() {
        try {
            db.writeItemsToCsv();
        } catch (IOException ex) {
            System.out.println("werkt niet");
        }
    }
    public void onEditItemButtonClick() {
        if(selectedItemId == 0)
            return;

        db.getItems().get((int)selectedItemId - 1).setTitle(this.titleField.getText());
        db.getItems().get((int)selectedItemId - 1).getAuthor().setFirstname(this.firstnameAuthorField.getText());
        db.getItems().get((int)selectedItemId - 1).getAuthor().setLastnamePrefix(this.prefixField.getText());
        db.getItems().get((int)selectedItemId - 1).getAuthor().setLastname(this.lastnameAuthorField.getText());

        this.itemsTableView.refresh();
        this.writeItemsToCsv();
    }

    public void onClearFieldsButtonClick() {
        this.titleField.setText("");
        this.firstnameAuthorField.setText("");
        this.prefixField.setText("");
        this.lastnameAuthorField.setText("");
    }
}
