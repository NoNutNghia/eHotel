package com.example.ehotel;

import com.example.ehotel.connectDB.ConnectDB;
import com.example.ehotel.model.Room;
import com.example.ehotel.service.RoomService;
import com.example.ehotel.service.impl.RoomServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main_ehotel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("eHotel application");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        RoomService roomService = new RoomServiceImpl();
//        List<Room> roomList = roomService.findAllRoom();
//        roomList.forEach(room -> {
//            System.out.println(room);
//        });

        Room room = roomService.getRoomById("72a11815-e9b8-47f1-9ec7-b4e3dc9dd7eb");
        System.out.println(room);

//        JSONParser jsonParser = new JSONParser();
//        try (FileReader fileReader = new FileReader("src/main/resources/backup/room.json")) {
//            Object object = jsonParser.parse(fileReader);
//            JSONArray jsonArray = (JSONArray) object;
//            jsonArray.forEach(o -> saveData((JSONObject) o));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        launch();
    }


    /* Backup data from file JSON" */
//    public static void saveData(JSONObject jsonObject) {
//        var connect = ConnectDB.connectDatabase("room");
//
//        var doc = new Document("_id", jsonObject.get("_id"));
//        doc.append("name", jsonObject.get("name"));
//        doc.append("price", jsonObject.get("price"));
//        doc.append("beds", jsonObject.get("beds"));
//        doc.append("img", jsonObject.get("img"));
//        doc.append("checkinRoom", jsonObject.get("checkinRoom"));
//        doc.append("checkoutRoom", jsonObject.get("checkoutRoom"));
//        doc.append("pointRating", 0.0);
//        doc.append("status", jsonObject.get("availability"));
//        doc.append("registerCustomer", null);
//        doc.append("rating", jsonObject.get("rating"));
//
//        connect.insertOne(doc);
//
//        ConnectDB.closeConnect();
//    }
}