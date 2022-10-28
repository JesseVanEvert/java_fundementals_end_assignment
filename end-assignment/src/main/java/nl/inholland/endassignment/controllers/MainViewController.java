package nl.inholland.endassignment.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import nl.inholland.endassignment.LibraryApplication;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainViewController implements Initializable{

    protected final Database db;
    @FXML
    private TabPane tabPane;
    private final User loggedInUser;

    public MainViewController(Database db, User loggedInUser) {
        this.db = db;
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.registerPanes();
    }

    private void registerPanes() {
        tabPane.getSelectionModel().clearSelection();
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                System.out.println("Tab selected: " + newValue.getText());

                if (newValue.getContent() == null) {
                    try {
                        String fileName = newValue.getText().toLowerCase(Locale.ROOT) + "-view.fxml";
                        FXMLLoader loader = new FXMLLoader();

                        switch (fileName) {
                            case "collection-view.fxml" ->
                                    loader.setController(new CollectionController(db));
                            case "lending-receiving-view.fxml" ->
                                    loader.setController(new LendingReceivingController(db, loggedInUser));
                            case "members-view.fxml" -> loader.setController(new MembersController());
                        }

                        Parent root = (Parent) loader.load(LibraryApplication.class.getResource(fileName).openStream());
                        newValue.setContent(root);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Content is already loaded. Update it if necessary.
                    Parent root = (Parent) newValue.getContent();
                    // Optionally get the controller from Map and manipulate the content
                    // via its controller.
                }
            }
        });
        // By default, select 1st tab and load its content.
        tabPane.getSelectionModel().selectFirst();
    }
}
