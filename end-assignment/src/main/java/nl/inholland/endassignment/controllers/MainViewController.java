package nl.inholland.endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainViewController implements Initializable {

    private final Database db;
    private User loggedInUser;
    private ObservableList<Item> items;
    private ObservableList<User> users;
    @FXML
    private Text welcomeText;
    @FXML
    private TextField memberIdTextField;
    @FXML
    private TextField lendItemCodeField;
    @FXML
    private Label feedbackLendItemLabel;

    public MainViewController(Database db, User loggedInUser) {
        this.db = db;
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.welcomeText.setText("Welcome " + loggedInUser.getFirstname());
        this.memberIdTextField.setText(Long.toString(this.loggedInUser.getId()));
        this.items = FXCollections.observableArrayList(db.getItems());
        this.users = FXCollections.observableArrayList(db.getUsers());
    }

    @FXML
    private void onLendItemButtonClick() {
        String itemCode = lendItemCodeField.getText();
        String memberId = memberIdTextField.getText();

        if(itemCode.equals("") || memberId.equals("")) {
            feedbackLendItemLabel.setText("Fill in both fields");
            feedbackLendItemLabel.setVisible(true);
            return;
        }

        Item selectedItem;
        User selectedUser;

        try {
            selectedItem = this.items.get(Integer.parseInt(itemCode));
            selectedUser = this.users.get(Integer.parseInt(memberId));
        } catch (NumberFormatException e) {
            feedbackLendItemLabel.setText("Both codes are numbers");
            feedbackLendItemLabel.setVisible(true);
            return;
        } catch (IndexOutOfBoundsException e) {
            feedbackLendItemLabel.setText("The item or user has not been found");
            feedbackLendItemLabel.setVisible(true);
            return;
        }

        if(selectedItem.getLendOutOn() != null) {
            long daysSinceLendOut = DAYS.between(selectedItem.getLendOutOn(), LocalDate.now());

            if( daysSinceLendOut > 21) {
                feedbackLendItemLabel.setText("The item is too late by: " + (daysSinceLendOut - 21) + " days");
            } else {
                feedbackLendItemLabel.setText("The item has been lend out");
            }

            feedbackLendItemLabel.setVisible(true);
            return;
        }

        this.items.get(Integer.parseInt(itemCode)).setLendOutOn(LocalDate.now());
        feedbackLendItemLabel.setText("The item " + selectedItem.getTitle() + "is lend out to " + selectedUser.getFirstname());
        feedbackLendItemLabel.setVisible(true);
    }
}
