package com.example.ehotel.service.impl;

import com.example.ehotel.connectDB.ConnectDB;
import com.example.ehotel.entities.CheckinRoom;
import com.example.ehotel.entities.CheckoutRoom;
import com.example.ehotel.service.CheckService;
import org.bson.Document;

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
}
