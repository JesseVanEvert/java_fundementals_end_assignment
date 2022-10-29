package nl.inholland.endassignment.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.inholland.endassignment.database.Database;
import nl.inholland.endassignment.models.Item;
import nl.inholland.endassignment.models.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class LendingReceivingController implements Initializable {

    private final Database db;
    protected User loggedInUser;
    /*@FXML
    private Text welcomeText;*/
    @FXML
    private TextField memberIdTextField;
    @FXML
    private TextField lendItemCodeField;
    @FXML
    private TextField receiveItemCodeField;
    @FXML
    private Label feedbackLendItemLabel;
    @FXML
    private Label feedbackReceiveItemLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this.welcomeText.setText("Welcome " + loggedInUser.getFirstname());
        this.memberIdTextField.setText(Long.toString(this.loggedInUser.getId()));
    }

    public LendingReceivingController(Database db, User loggedInUser) {
        this.db = db;
        this.loggedInUser = loggedInUser;
    }

    @FXML
    private void onReceiveItemButtonClick() {
        String itemCode = receiveItemCodeField.getText();

        Item selectedItem = this.getItemFromItems(itemCode);
        if(selectedItem == null)
            return;

        db.getItems().get((int)selectedItem.getId() - 1).setLendOutOn(null);
        feedbackLendItemLabel.setText("The item: " + selectedItem.getTitle() + " has been received");
        feedbackLendItemLabel.setVisible(true);
    }

    //Lend item part
    @FXML
    private void onLendItemButtonClick() {
        String itemCode = lendItemCodeField.getText();
        String memberId = memberIdTextField.getText();

        if(!this.areLendItemFieldsAreFilledIn(itemCode, memberId))
            return;

        Item selectedItem = this.getItemFromItems(itemCode);
        if(selectedItem == null)
            return;

        User selectedUser = this.getUserFromUsers(memberId);
        if(selectedUser == null)
            return;

        if(!this.isTheItemAvailable(selectedItem))
            return;

        db.getItems().get((int)selectedItem.getId() - 1).setLendOutOn(LocalDate.now());
        feedbackLendItemLabel.setText("The item " + selectedItem.getTitle() + " is lend out to " + selectedUser.getFirstname());
        feedbackLendItemLabel.setVisible(true);
    }

    private boolean isTheItemAvailable(Item selectedItem) {
        if(selectedItem.getLendOutOn() != null) {
            long daysSinceLendOut = DAYS.between(selectedItem.getLendOutOn(), LocalDate.now());

            if( daysSinceLendOut > 21) {
                feedbackLendItemLabel.setText("The item is too late by: " + (daysSinceLendOut - 21) + " days");
            } else {
                feedbackLendItemLabel.setText("The item has been lend out");
            }

            feedbackLendItemLabel.setVisible(true);
            return false;
        }

        return true;
    }
    private Item getItemFromItems(String itemCode) {
        Item selectedItem;

        try {
            selectedItem = db.getItems().get(Integer.parseInt(itemCode) - 1);
        } catch (NumberFormatException e) {
            feedbackLendItemLabel.setText("The item code is a number");
            feedbackLendItemLabel.setVisible(true);
            return null;
        } catch (IndexOutOfBoundsException e) {
            feedbackLendItemLabel.setText("The item has not been found");
            feedbackLendItemLabel.setVisible(true);
            return null;
        }

        return selectedItem;
    }

    private User getUserFromUsers(String memberId) {
        User selectedUser;

        try {
            selectedUser = db.getUsers().get(Integer.parseInt(memberId) - 1);
        } catch (NumberFormatException e) {
            feedbackLendItemLabel.setText("The member id is a number");
            feedbackLendItemLabel.setVisible(true);
            return null;
        } catch (IndexOutOfBoundsException e) {
            feedbackLendItemLabel.setText("The member has not been found");
            feedbackLendItemLabel.setVisible(true);
            return null;
        }

        return selectedUser;
    }

    private boolean areLendItemFieldsAreFilledIn(String itemCode, String memberId) {
        if(itemCode.equals("") || memberId.equals("")) {
            feedbackLendItemLabel.setText("Fill in both fields");
            feedbackLendItemLabel.setVisible(true);
            return false;
        }

        return true;
    }
}
