package com.example.ehotel.controller;

import com.example.ehotel.model.Customer;
import com.example.ehotel.model.Room;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CustomerController implements Initializable {

    @FXML
    private TableView<Room> roomRegister;

    @FXML
    private TableColumn<Room, String> _id;

    @FXML
    private TableColumn<Room, Double> price;

    @FXML
    private TableColumn<Room, String> name;

    @FXML
    private TableColumn<Room, Integer> beds;

    @FXML
    private TableColumn<Room, String> status;

    @FXML
    private TextField fullNameCustomer;

    @FXML
    private TextField reserveAt;

    @FXML
    private TextField dateOfBirthCustomer;

    @FXML
    private TextField identCustomer;

    @FXML
    private TextField phoneNumberCustomer;

    @FXML
    private TextField searchIdentityCustomer;

    private final RoomService roomService = new RoomServiceImpl();
    private ObservableList<Room> observableList = FXCollections.observableArrayList();
    private List<Customer> customerList = new ArrayList<>();
    private final CustomerService customerService = new CustomerServiceImpl();


    public void searchCustomer(ActionEvent actionEvent) {
        String idNumberCustomer = searchIdentityCustomer.getText();

        Optional<Customer> foundCustomer = customerList.stream()
                .filter(customer -> Objects.equals(customer.getIdentityNumber(), idNumberCustomer))
                .findFirst();

        if(foundCustomer.isPresent()) {
            Room foundRoom = observableList.stream().filter(room ->
                    Objects.equals(room.getRegisterCustomer(), foundCustomer.get().get_id())
            ).findFirst().get();

            observableList = FXCollections.observableArrayList(foundRoom);
            showRoom(observableList);
            clearField();
            searchIdentityCustomer.clear();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableList = roomService.findRoomByStatusRegister();
        showRoom(observableList);
        customerList = getCustomerRegister(observableList);
    }

    public void backToMainPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ehotel/main_ehotel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setTitle("eHotel Application");
        window.setScene(scene);
        window.show();
    }

    private void showRoom(ObservableList<Room> roomObservableList) {

        _id.setCellValueFactory(new PropertyValueFactory<>("_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        beds.setCellValueFactory(new PropertyValueFactory<>("beds"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        roomRegister.setItems(roomObservableList);

    }

    private void showInfoCustomer(Customer customer) {
        fullNameCustomer.setText(customer.getFullName());
        reserveAt.setText(customer.getReserveAt());
        dateOfBirthCustomer.setText(customer.getDateOfBirth());
        identCustomer.setText(customer.getIdentityNumber());
        phoneNumberCustomer.setText(customer.getPhoneNumber());
    }

    public void checkInfoCustomer(ActionEvent actionEvent) {
        ObservableList<Room> selectedRoom = roomRegister.getSelectionModel().getSelectedItems();

        Customer customer = customerService.getCustomerById(selectedRoom.get(0).getRegisterCustomer());

        showInfoCustomer(customer);
    }

    private List<Customer> getCustomerRegister(ObservableList<Room> roomObservableList) {
        List<Customer> getData = new ArrayList<>();
        roomObservableList.forEach(room -> {
            Customer customer = customerService.getCustomerById(room.getRegisterCustomer());
            getData.add(customer);
        });

        return getData;
    }

    private void clearField() {
        fullNameCustomer.clear();
        reserveAt.clear();
        dateOfBirthCustomer.clear();
        identCustomer.clear();
        phoneNumberCustomer.clear();
    }
}
