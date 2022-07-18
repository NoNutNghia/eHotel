package com.example.ehotel.controller;

import com.example.ehotel.model.Room;
import com.example.ehotel.service.RoomService;
import com.example.ehotel.service.impl.RoomServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticalController implements Initializable {

    @FXML
    private BarChart barChartTurnover;

    @FXML
    private BarChart barChartCustomer;

    private final XYChart.Series dataTurnover = new XYChart.Series();

    private final XYChart.Series dataCustomers = new XYChart.Series();

    private final RoomService roomService = new RoomServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Room> roomList = roomService.findAllRoom();

        initDataTurnover(roomList);
        initDataCustomers(roomList);
    }

    public void backToMainPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ehotel/main_ehotel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setTitle("eHotel Application");
        window.setScene(scene);
        window.show();
    }

    private void initDataTurnover(List<Room> roomList) {
        dataTurnover.setName("Turnover with each room");

        roomList.forEach(room -> {
            dataTurnover.getData().add(new XYChart.Data(room.getName(), room.getTotalTurnover()));
        });

        barChartTurnover.getData().add(dataTurnover);
    }

    private void initDataCustomers(List<Room> roomList) {
        dataCustomers.setName("Register customer with each room");

        roomList.forEach(room -> {
            dataCustomers.getData().add(new XYChart.Data(room.getName(), room.getCheckinRoom().size()));
        });

        barChartCustomer.getData().add(dataCustomers);
    }

}
