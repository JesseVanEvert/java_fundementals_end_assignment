module nl.inholland.endassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens nl.inholland.endassignment to javafx.fxml;
    exports nl.inholland.endassignment;
    exports nl.inholland.endassignment.controllers;
    opens nl.inholland.endassignment.controllers to javafx.fxml;

    exports nl.inholland.endassignment.models;
    opens nl.inholland.endassignment.models to javafx.fxml;
}