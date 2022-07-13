package com.example.ehotel.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button reservation;

    @FXML
    private Button room;

    @FXML
    private Button statistical;


    public void redirectToRoomMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ehotel/room_main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setTitle("eHotel Application");
        window.setScene(scene);
        window.show();
    }
}
