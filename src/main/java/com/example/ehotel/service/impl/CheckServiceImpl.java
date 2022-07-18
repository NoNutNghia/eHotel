package com.example.ehotel.service.impl;

import com.example.ehotel.connectDB.ConnectDB;
import com.example.ehotel.entities.CheckinRoom;
import com.example.ehotel.entities.CheckoutRoom;
import com.example.ehotel.model.Checkin;
import com.example.ehotel.service.CheckService;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

public class CheckServiceImpl implements CheckService {

    @Override
    public void createCheckin(CheckinRoom checkinRoom, String roomId) {
        var connect = ConnectDB.connectDatabase("checkin");

        var doc = new Document("_id", checkinRoom.get_id());
        doc.append("timeCheckin", checkinRoom.getTimeCheckin());
        doc.append("customerId", checkinRoom.getCustomerId());
        doc.append("roomId", roomId);

        connect.insertOne(doc);

        ConnectDB.closeConnect();
    }

    @Override
    public void createCheckout(CheckoutRoom checkoutRoom, String roomId) {
        var connect = ConnectDB.connectDatabase("checkout");

        var doc = new Document("_id", checkoutRoom.get_id());
        doc.append("timeCheckout", checkoutRoom.getTimeCheckout());
        doc.append("customerId", checkoutRoom.getCustomerId());
        doc.append("roomId", roomId);

        connect.insertOne(doc);

        ConnectDB.closeConnect();
    }

    @Override
    public Checkin getCheckinByRoomId(String roomId) {
        var connect = ConnectDB.connectDatabase("checkin");

        Bson filter = Filters.eq("roomId", roomId);

        Document doc = connect.find(filter).first();

        Checkin checkin = returnCheckinObject(doc);

        ConnectDB.closeConnect();

        return checkin;
    }

    private Checkin returnCheckinObject(Document document) {
        Checkin checkin = new Checkin();

        checkin.set_id(document.get("_id").toString());
        checkin.setTimeCheckin(document.get("timeCheckin").toString());
        checkin.setRoomId(document.get("roomId").toString());
        checkin.setCustomerId(document.get("customerId").toString());

        return checkin;
    }
}
