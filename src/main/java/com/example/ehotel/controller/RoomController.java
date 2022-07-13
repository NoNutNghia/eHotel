package com.example.ehotel.controller;

import com.example.ehotel.enums.StatusRoom;
import com.example.ehotel.model.Room;
import com.example.ehotel.request.RoomDTO;
import com.example.ehotel.service.RoomService;
import com.example.ehotel.service.impl.RoomServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RoomController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField bedsField;

    @FXML
    private TextField statusField;

    @FXML
    private Button editInfoRoom;

    @FXML
    private Button checkinRoom;

    @FXML
    private Button checkoutRoom;

    @FXML
    private Button registerRoom;

    @FXML
    private Button backToMain;

    @FXML
    private Button updateInfoRoom;

    @FXML
    private Button cancelEditRoom;

    @FXML
    private ChoiceBox<String> statusChoice;

    private final String[] statusRoom = {"available", "maintain"};
    private static String roomId;
    private RoomService roomService = new RoomServiceImpl();

    public void chooseRoom(MouseEvent mouseEvent) {
        roomId = mouseEvent.getPickResult().getIntersectedNode().getId();
        if(roomId != null) {
            Room room = roomService.getRoomById(roomId);
            showInfo(room);
        }

    }

    private void showInfo(Room room) {
        nameField.setText(room.getName());
        bedsField.setText(room.getBeds().toString());
        priceField.setText(room.getPrice().toString());
        statusField.setText(room.getStatus());

        statusChoice.setValue(room.getStatus());

        checkAction(room.getStatus());
    }

    private void checkAction(String statusRoom) {
        if(Objects.equals(statusRoom, StatusRoom.AVAILABLE.getStatusRoom())) {
            registerRoom.setDisable(false);
            editInfoRoom.setDisable(false);
            checkinRoom.setDisable(true);
            checkoutRoom.setDisable(true);
        }
        else if(Objects.equals(statusRoom, StatusRoom.MAINTAIN.getStatusRoom())) {
            registerRoom.setDisable(true);
            editInfoRoom.setDisable(false);
            checkinRoom.setDisable(true);
            checkoutRoom.setDisable(true);
        }
        else if(Objects.equals(statusRoom, StatusRoom.REGISTER.getStatusRoom())) {
            registerRoom.setDisable(true);
            editInfoRoom.setDisable(true);
            checkinRoom.setDisable(false);
            checkoutRoom.setDisable(true);
        }
        else {
            editInfoRoom.setDisable(true);
            registerRoom.setDisable(true);
            checkinRoom.setDisable(true);
            checkoutRoom.setDisable(false);
        }
    }

    public void editInfo(ActionEvent actionEvent) {

        nameField.setEditable(true);
        priceField.setEditable(true);
        bedsField.setEditable(true);

        editInfoRoom.setVisible(false);
        updateInfoRoom.setVisible(true);
        cancelEditRoom.setVisible(true);
        statusChoice.setVisible(true);

        statusField.setVisible(false);
    }

    public void backToMainSite(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ehotel/main_ehotel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setTitle("eHotel Application");
        window.setScene(scene);
        window.show();
    }

    public void updateInfo(ActionEvent actionEvent) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(nameField.getText());
        roomDTO.setBeds( Integer.parseInt(bedsField.getText()));
        roomDTO.setPrice( Double.parseDouble(priceField.getText()));
        roomDTO.setStatus(statusChoice.getValue());

        roomService.updateRoom(roomDTO, roomId);
        Room room = roomService.getRoomById(roomId);
        showInfo(room);
        cancelEdit(actionEvent);
    }

    public void cancelEdit(ActionEvent actionEvent) {
        nameField.setEditable(false);
        priceField.setEditable(false);
        bedsField.setEditable(false);

        editInfoRoom.setVisible(true);
        updateInfoRoom.setVisible(false);
        cancelEditRoom.setVisible(false);
        statusChoice.setVisible(false);

        statusField.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusChoice.getItems().addAll(statusRoom);
    }
}
