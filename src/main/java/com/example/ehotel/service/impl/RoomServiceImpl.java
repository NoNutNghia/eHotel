package com.example.ehotel.service.impl;

import com.example.ehotel.connectDB.ConnectDB;
import com.example.ehotel.entities.CheckinRoom;
import com.example.ehotel.entities.CheckoutRoom;
import com.example.ehotel.entities.RegisterCustomer;
import com.example.ehotel.model.Room;
import com.example.ehotel.request.RoomDTO;
import com.example.ehotel.service.CheckService;
import com.example.ehotel.service.RoomService;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.SimpleDateFormat;
import java.util.*;

public class RoomServiceImpl implements RoomService {

    private CheckService checkService = new CheckServiceImpl();

    @Override
    public void reserveRoom(String customerId, String roomId) {
        var connectRoom = ConnectDB.connectDatabase("room");


        Bson filter = Filters.eq("_id", roomId);

        Bson update = Updates.combine(
                Updates.set("registerCustomer", customerId),
                Updates.set("status", "reservation")

        );

        var connectCustomer = ConnectDB.connectDatabase("customer");
        Bson filterCustomer = Filters.eq("_id", customerId);
        Bson updateCustomer = Updates.combine(
                Updates.set("checkinAt", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime())),
                Updates.set("roomId", roomId)
        );

        connectCustomer.findOneAndUpdate(filterCustomer, updateCustomer);
        connectRoom.findOneAndUpdate(filter, update);

        ConnectDB.closeConnect();
    }

    @Override
    public void checkinRoom(String roomId, String customerId) {
        var connect = ConnectDB.connectDatabase("room");

        Bson filter = Filters.eq("_id", roomId);

        CheckinRoom checkinRoom = new CheckinRoom();
        checkinRoom.set_id(UUID.randomUUID().toString());
        checkinRoom.setTimeCheckin(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        checkinRoom.setCustomerId(customerId);

        Bson update = Updates.combine(
                Updates.push("checkinRoom", checkinRoom),
                Updates.set("status", "in-use")
        );

        connect.findOneAndUpdate(filter, update);

        checkService.createCheckin(checkinRoom, roomId);

        ConnectDB.closeConnect();
    }

    @Override
    public void checkoutRoom(String roomId, String customerId) {
        var connect = ConnectDB.connectDatabase("room");

        Bson filter = Filters.eq("_id", roomId);

        CheckoutRoom checkoutRoom = new CheckoutRoom();
        checkoutRoom.set_id(UUID.randomUUID().toString());
        checkoutRoom.setTimeCheckout(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        checkoutRoom.setCustomerId(customerId);

        Bson update = Updates.combine(
                Updates.push("checkoutRoom", checkoutRoom),
                Updates.set("registerCustomer", null),
                Updates.set("status", "available")
        );

        var connectCustomer = ConnectDB.connectDatabase("customer");
        Bson filterCustomer = Filters.eq("_id", customerId);
        Bson updateCustomer = Updates.set("checkoutAt", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));

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
        room.setRating((List<Integer>) doc.get("rating"));
        room.setRegisterCustomer((String) doc.get("registerCustomer"));

        return room;
    }

}
