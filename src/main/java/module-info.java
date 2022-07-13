module com.example.ehotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires mongo.java.driver;
    requires static lombok;
    requires json.simple;
    opens com.example.ehotel to javafx.fxml;
    opens com.example.ehotel.controller to javafx.fxml;
    exports com.example.ehotel;
    exports com.example.ehotel.controller;
}