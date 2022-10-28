package nl.inholland.endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class CollectionController implements Initializable {

    private final Database db;
    private final User loggedInUser;
    private ObservableList<Item> items;
    @FXML
    private TableView<Item> itemsTableView;

    public CollectionController(Database db, User loggedInUser) {
        this.db = db;
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.items = FXCollections.observableArrayList(db.getItems());
        this.itemsTableView.setItems(items);
        this.itemsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


}
