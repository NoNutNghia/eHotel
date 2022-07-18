package com.example.ehotel.controller;

import com.example.ehotel.model.Room;
import com.example.ehotel.request.CustomerDTO;
import com.example.ehotel.service.CustomerService;
import com.example.ehotel.service.RoomService;
import com.example.ehotel.service.impl.CustomerServiceImpl;
import com.example.ehotel.service.impl.RoomServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    @FXML
    private TableView<Room> room;

    @FXML
    private TableColumn<Room, String> _id;

    @FXML
    private TableColumn<Room, String> name;

    @FXML
    private TableColumn<Room, Integer> beds;

    @FXML
    private TableColumn<Room, Integer> price;

    @FXML
    private TableColumn<Room, Double> rating;

    @FXML
    private Label bedsLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label fromPriceLabel;

    @FXML
    private Label toPriceLabel;

    @FXML
    private TextField fullNameCustomer;

    @FXML
    private TextField identNumberCustomer;

    @FXML
    private TextField phoneNumCustomer;

    @FXML
    private DatePicker dateBirthCustomer;

    @FXML
    private TextField fromPrice;

    @FXML
    private TextField toPrice;

    @FXML
    private Button registerCustomer;

    @FXML
    private Button searchRoom;

    @FXML
    private ChoiceBox<String> bedsChoice;

    ObservableList<Room> roomObservableList = FXCollections.observableArrayList();

    private final String[] bedsChoiceBox = {"1", "2", "3"};

    private final RoomService roomService = new RoomServiceImpl();
    private final CustomerService customerService = new CustomerServiceImpl();

    public void backToMainSite(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ehotel/main_ehotel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setTitle("eHotel Application");
        window.setScene(scene);
        window.show();
    }

    public void searchRoom(ActionEvent actionEvent) {
        String choice = bedsChoice.getValue();
        String fromPriceValue = fromPrice.getText();
        String toPriceValue = toPrice.getText();
        if(choice != null || !fromPriceValue.isEmpty() || !toPriceValue.isEmpty()) {
            roomObservableList = roomService.findRoomBySearch(choice, fromPriceValue, toPriceValue);

            showRoom(roomObservableList);
        }
    }

    public void registerRoom(ActionEvent actionEvent) {
        ObservableList<Room> roomSelect = room.getSelectionModel().getSelectedItems();
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setDateOfBirth(dateBirthCustomer.getValue().toString());
        customerDTO.setFullName(fullNameCustomer.getText());
        customerDTO.setIdentityNumber(identNumberCustomer.getText());
        customerDTO.setPhoneNumber(phoneNumCustomer.getText());

        String idCustomer = customerService.createCustomer(customerDTO);

        roomService.reserveRoom(idCustomer, roomSelect.get(0).get_id());

        roomObservableList = roomService.findRoomByStatus();
        clearField();
        showRoom(roomObservableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bedsChoice.getItems().addAll(bedsChoiceBox);
        roomObservableList = roomService.findRoomByStatus();
        showRoom(roomObservableList);
    }

    private void showRoom(ObservableList<Room> roomObservableList) {

        _id.setCellValueFactory(new PropertyValueFactory<>("_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        beds.setCellValueFactory(new PropertyValueFactory<>("beds"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        rating.setCellValueFactory(new PropertyValueFactory<>("pointRating"));

        room.setItems(roomObservableList);
    }

    private void clearField() {
        dateBirthCustomer.setValue(null);
        fullNameCustomer.clear();
        identNumberCustomer.clear();
        phoneNumCustomer.clear();
    }
}
