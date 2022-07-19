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
        getResource("/com/example/ehotel/room_main.fxml", actionEvent);

    }

    public void redirectToReservationPage(ActionEvent actionEvent) throws IOException {
        getResource("/com/example/ehotel/register_room.fxml", actionEvent);
    }

    public void getResource(String resource, ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setTitle("eHotel Application");
        window.setScene(scene);
        window.show();
    }

    public void redirectToStatisticalPage(ActionEvent actionEvent) throws IOException {
        getResource("/com/example/ehotel/statistical_ehotel.fxml", actionEvent);
    }

    public void redirectToCustomerInfoPage(ActionEvent actionEvent) throws IOException {
        getResource("/com/example/ehotel/customer_ehotel.fxml", actionEvent);
    }
}
