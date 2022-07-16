package com.example.ehotel.service.impl;

import com.example.ehotel.connectDB.ConnectDB;
import com.example.ehotel.entities.CheckinRoom;
import com.example.ehotel.entities.CheckoutRoom;
import com.example.ehotel.enums.StatusRoom;
import com.example.ehotel.model.Room;
import com.example.ehotel.request.RoomDTO;
import com.example.ehotel.service.CheckService;
import com.example.ehotel.service.RoomService;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.SimpleDateFormat;
import java.util.*;

public class RoomServiceImpl implements RoomService {

    private final CheckService checkService = new CheckServiceImpl();

    @Override
    public void reserveRoom(String customerId, String roomId) {
        var connectRoom = ConnectDB.connectDatabase("room");


        Bson filter = Filters.eq("_id", roomId);

        Bson update = Updates.combine(
                Updates.set("registerCustomer", customerId),
                Updates.set("status", StatusRoom.REGISTER.getStatusRoom())

        );

        var connectCustomer = ConnectDB.connectDatabase("customer");
        Bson filterCustomer = Filters.eq("_id", customerId);
        Bson updateCustomer = Updates.combine(
                Updates.set("idRoom", roomId)
        );

        connectCustomer.findOneAndUpdate(filterCustomer, updateCustomer);
        connectRoom.findOneAndUpdate(filter, update);

        ConnectDB.closeConnect();
    }

    @Override
    public void checkinRoom(String roomId, String customerId) {
        var connect = ConnectDB.connectDatabase("room");

        var connectCustomer = ConnectDB.connectDatabase("customer");

        Bson filter = Filters.eq("_id", roomId);
        Bson filerCustomer = Filters.eq("_id", customerId);

        String checkinAt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        CheckinRoom checkinRoom = new CheckinRoom(UUID.randomUUID().toString(), checkinAt, customerId);

        Bson update = Updates.combine(
                Updates.push("checkinRoom", checkinRoom),
                Updates.set("status", "in-use")
        );

        Bson updateCustomer = Updates.set("checkinAt", checkinAt);

        System.out.println(update);
        System.out.println(filter);

        connect.findOneAndUpdate(filter, update);
        connectCustomer.findOneAndUpdate(filerCustomer, updateCustomer);

        checkService.createCheckin(checkinRoom, roomId);

        ConnectDB.closeConnect();
    }

    @Override
    public void checkoutRoom(String roomId, String customerId, String rating) {
        var connect = ConnectDB.connectDatabase("room");

        Bson filter = Filters.eq("_id", roomId);

        String checkoutAt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

        CheckoutRoom checkoutRoom = new CheckoutRoom();
        checkoutRoom.set_id(UUID.randomUUID().toString());
        checkoutRoom.setTimeCheckout(checkoutAt);
        checkoutRoom.setCustomerId(customerId);

        Bson update = Updates.combine(
                Updates.push("checkoutRoom", checkoutRoom),
                Updates.set("registerCustomer", null),
                Updates.set("status", "available"),
                Updates.push("rating", Double.parseDouble(rating))
        );


        var connectCustomer = ConnectDB.connectDatabase("customer");
        Bson filterCustomer = Filters.eq("_id", customerId);
        Bson updateCustomer = Updates.set("checkoutAt", checkoutAt);

        connectCustomer.findOneAndUpdate(filterCustomer, updateCustomer);
        connect.findOneAndUpdate(filter, update);

        checkService.createCheckout(checkoutRoom, roomId);

        ConnectDB.closeConnect();
    }

    @Override
    public void updateRoom(RoomDTO roomDTO, String roomId) {
        var connect = ConnectDB.connectDatabase("room");

        Bson filter = Filters.eq(roomId);
        Bson update = Updates.combine(
                Updates.set("name", roomDTO.getName()),
                Updates.set("price", roomDTO.getPrice()),
                Updates.set("beds", roomDTO.getBeds()),
                Updates.set("status", roomDTO.getStatus())
        );

        connect.findOneAndUpdate(filter, update);

        ConnectDB.closeConnect();
    }

    @Override
    public Room getRoomById(String roomId) {
        var connect = ConnectDB.connectDatabase("room");
        Bson filter = Filters.eq("_id", roomId);
        var doc = connect.find(filter).first();
        Room room = new Room();
        if(doc != null) {
            room = returnObjectRoom(doc);
        }

        ConnectDB.closeConnect();
        return room;
    }

    @Override
    public List<Room> findAllRoom() {
        List<Room> roomList = new ArrayList<>();
        var connect = ConnectDB.connectDatabase("room");
        try(MongoCursor<Document> cursor = connect.find().iterator()) {
            while (cursor.hasNext()) {
                var doc = cursor.next();

                Room room = returnObjectRoom(doc);
                roomList.add(room);
            }
        }

        ConnectDB.closeConnect();
        return roomList;
    }

    @Override
    public ObservableList<Room> findRoomByStatus() {
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList();
        var connect = ConnectDB.connectDatabase("room");
        Bson filter = Filters.eq("status", StatusRoom.AVAILABLE.getStatusRoom());

        MongoCursor<Document> cursor = connect.aggregate(Arrays.asList(
                Aggregates.match(filter)
        )).iterator();

        while (cursor.hasNext()) {
            var doc = cursor.next();

            Room room = returnObjectRoom(doc);

            roomObservableList.add(room);
        }
        return roomObservableList;
    }

    public Room returnObjectRoom(Document doc) {

        Room room = new Room();
        room.set_id(doc.get("_id").toString());
        room.setName(doc.get("name").toString());
        room.setPrice(Double.parseDouble(doc.get("price").toString()));
        room.setBeds(Integer.parseInt(doc.get("beds").toString()));
        room.setImg(doc.get("img").toString());
        room.setCheckinRoom((List<CheckinRoom>) doc.get("checkinRoom"));
        room.setCheckoutRoom((List<CheckoutRoom>) doc.get("checkoutRoom"));
        room.setPointRating((Double) doc.get("pointRating"));
        room.setStatus(doc.get("status").toString());
        room.setRating((List<Double>) doc.get("rating"));
        room.setRegisterCustomer((String) doc.get("registerCustomer"));

        return room;
    }

}
