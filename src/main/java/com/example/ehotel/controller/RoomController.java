package com.example.ehotel.controller;

import com.example.ehotel.enums.StatusRoom;
import com.example.ehotel.model.Customer;
import com.example.ehotel.model.Room;
import com.example.ehotel.request.RoomDTO;
import com.example.ehotel.service.CustomerService;
import com.example.ehotel.service.RoomService;
import com.example.ehotel.service.impl.CustomerServiceImpl;
import com.example.ehotel.service.impl.RoomServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private Button backToMain;

    @FXML
    private Button updateInfoRoom;

    @FXML
    private Button cancelRegister;

    @FXML
    private Button cancelEditRoom;

    @FXML
    private Button checkInfoCustomer;

    @FXML
    private ChoiceBox<String> statusChoice;

    private final String[] statusRoom = {"available", "maintain"};
    private static String roomId;
    private RoomService roomService = new RoomServiceImpl();
    private CustomerService customerService = new CustomerServiceImpl();

    public void chooseRoom(MouseEvent mouseEvent) {
        roomId = mouseEvent.getPickResult().getIntersectedNode().getId();
        if(roomId != null) {
            Room room = roomService.getRoomById(roomId);
            showInfo(room);
            checkoutRoom.setId(roomId);
            checkinRoom.setId(roomId);
            if(room.getRegisterCustomer() != null) {
                checkInfoCustomer.setId(room.getRegisterCustomer());
            }
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
            editInfoRoom.setDisable(false);
            checkinRoom.setDisable(true);
            checkoutRoom.setDisable(true);
            checkoutRoom.setVisible(true);
            cancelRegister.setVisible(false);
            checkInfoCustomer.setVisible(false);
        }
        else if(Objects.equals(statusRoom, StatusRoom.MAINTAIN.getStatusRoom())) {
            editInfoRoom.setDisable(false);
            checkinRoom.setDisable(true);
            checkoutRoom.setDisable(true);
            checkoutRoom.setVisible(true);
            cancelRegister.setVisible(false);
            checkInfoCustomer.setVisible(false);
        }
        else if(Objects.equals(statusRoom, StatusRoom.REGISTER.getStatusRoom())) {
            editInfoRoom.setDisable(true);
            checkinRoom.setDisable(false);
            checkoutRoom.setVisible(false);
            cancelRegister.setVisible(true);
            checkInfoCustomer.setVisible(true);
        }
        else {
            editInfoRoom.setDisable(true);
            checkinRoom.setDisable(true);
            checkoutRoom.setDisable(false);
            checkoutRoom.setVisible(true);
            cancelRegister.setVisible(false);
            checkInfoCustomer.setVisible(false);
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
        checkInfoCustomer.setVisible(false);
    }

    public void cancelRegister(ActionEvent actionEvent) {
        checkoutRoom.setVisible(true);
        cancelRegister.setVisible(false);

        roomService.cancelRegister(roomId);
        Room room = roomService.getRoomById(roomId);

        showInfo(room);
    }

    public void checkInRoom(ActionEvent actionEvent) {
        roomService.checkinRoom(checkinRoom.getId(), checkInfoCustomer.getId());
        Room room = roomService.getRoomById(checkinRoom.getId());
        showInfo(room);
    }

    public void checkOutRoom(ActionEvent actionEvent) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Rating from customer");
        textInputDialog.setContentText("Customer Rating (0 - 10): ");

        Optional<String> rating = textInputDialog.showAndWait();
        Double price;
        if(rating.isPresent()) {
            price = roomService.checkoutRoom(checkoutRoom.getId(), checkInfoCustomer.getId(), rating.get());
        } else {
            price = roomService.checkoutRoom(checkoutRoom.getId(), checkInfoCustomer.getId(), "10");
        }

        Dialog<Void> dialog = new Dialog<Void>();
        DialogPane dialogPane = new DialogPane();
        Label label = new Label();
        label.setText("Customer must pay : " + price + "\n");
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest((e) -> {
            dialog.hide();
        });
        dialog.setTitle("Customer Info");
        dialogPane.setContent(label);
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();

        Room room = roomService.getRoomById(checkoutRoom.getId());
        showInfo(room);
    }

    public void checkInfoCustomer(ActionEvent actionEvent) {
        Dialog<Void> dialog = new Dialog<Void>();
        DialogPane dialogPane = new DialogPane();
        Label label = new Label();

        Customer customer = customerService.getCustomerById(checkInfoCustomer.getId());
        label.setText(
                        "Full name: \t" + customer.getFullName()  + "\n" +
                        "Identity number: \t" + customer.getIdentityNumber() + "\n" +
                        "Phone number: \t" + customer.getPhoneNumber() + "\n" +
                        "Date of birth: \t" + customer.getDateOfBirth() + "\n" +
                        "Reserve at: \t" + customer.getReserveAt() + ""

            );
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest((e) -> {
            dialog.hide();
        });
        dialog.setTitle("Customer Info");
        dialogPane.setContent(label);
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
    }
}
