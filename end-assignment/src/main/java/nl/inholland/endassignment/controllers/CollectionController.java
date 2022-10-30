package nl.inholland.endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.Author;
import nl.inholland.endassignment.models.Item;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
    @FXML
    private Label feedbackCollectionLabel;
    private final Logger log = Logger.getLogger(this.getClass().getName());

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

    @FXML
    private void onAddItemButtonClick() {
        Author author = new Author(
                db.getItems().size() + 1L,
                this.firstnameAuthorField.getText(),
                this.prefixField.getText(),
                this.lastnameAuthorField.getText()
        );

        if(!this.areRequiredFieldFilledIn())
            return;

        Item item = new Item(db.getItems().get(db.getItems().size() - 1).getId() + 1, titleField.getText(), author);
        db.getItems().add(item);

        this.itemsTableView.refresh();
        this.feedbackCollectionLabel.setText("Added item: " + item.getTitle());
        this.feedbackCollectionLabel.setVisible(true);
        this.writeItemsToCsv();
    }

    @FXML
    private void onDeleteItemButtonClick() {
        if(selectedItemId == 0)
            return;

        db.getItems().remove((int)selectedItemId - 1);

        this.itemsTableView.setItems(db.getItems());
        this.feedbackCollectionLabel.setText("Deleted item: " + this.titleField.getText());
        this.feedbackCollectionLabel.setVisible(true);
        this.writeItemsToCsv();
    }

    @FXML
    private void onEditItemButtonClick() {
        if(selectedItemId == 0)
            return;

        if(!this.areRequiredFieldFilledIn())
            return;

        db.getItems().get((int)selectedItemId - 1).setTitle(this.titleField.getText());
        db.getItems().get((int)selectedItemId - 1).getAuthor().setFirstname(this.firstnameAuthorField.getText());
        db.getItems().get((int)selectedItemId - 1).getAuthor().setLastnamePrefix(this.prefixField.getText());
        db.getItems().get((int)selectedItemId - 1).getAuthor().setLastname(this.lastnameAuthorField.getText());

        this.itemsTableView.refresh();
        this.feedbackCollectionLabel.setText("Edited item: " + this.titleField.getText());
        this.feedbackCollectionLabel.setVisible(true);
        this.writeItemsToCsv();
    }

    @FXML
    private void onClearFieldsButtonClick() {
        this.titleField.setText("");
        this.firstnameAuthorField.setText("");
        this.prefixField.setText("");
        this.lastnameAuthorField.setText("");
    }

    private void writeItemsToCsv() {
        try {
            db.writeItemsToCsv();
        } catch (IOException ex) {
            this.log.warning("Writing to items or authors csv failed at: " + LocalDate.now() + " exception: " + ex);
        }
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

    private boolean areRequiredFieldFilledIn() {
        if(
                this.firstnameAuthorField.getText().strip().equals("") ||
                        this.lastnameAuthorField.getText().strip().equals("") ||
                        this.titleField.getText().equals("")
        ) {
            this.feedbackCollectionLabel.setText("All fields except for prefix have to be filled");
            this.feedbackCollectionLabel.setVisible(true);
            return false;
        }

        return true;
    }
}
