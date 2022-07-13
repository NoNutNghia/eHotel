package com.example.ehotel;

import com.example.ehotel.connectDB.ConnectDB;
import javafx.application.Application;
import javafx.stage.Stage;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;


public class BackupApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

    }

    public static void main(String[] args) throws IOException {

        JSONParser jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader("src/main/resources/backup/room.json")) {
            Object object = jsonParser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) object;
            jsonArray.forEach(o -> saveData((JSONObject) o));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        launch();
    }


    /* Backup data from file JSON" */
    public static void saveData(JSONObject jsonObject) {
        var connect = ConnectDB.connectDatabase("room");

        var doc = new Document("_id", jsonObject.get("_id"));
        doc.append("name", jsonObject.get("name"));
        doc.append("price", jsonObject.get("price"));
        doc.append("beds", jsonObject.get("beds"));
        doc.append("img", jsonObject.get("img"));
        doc.append("checkinRoom", jsonObject.get("checkinRoom"));
        doc.append("checkoutRoom", jsonObject.get("checkoutRoom"));
        doc.append("pointRating", 0.0);
        doc.append("status", "available");
        doc.append("registerCustomer", null);
        doc.append("rating", jsonObject.get("rating"));

        connect.insertOne(doc);

        ConnectDB.closeConnect();
    }
}