package com.example.ehotel.service;

import com.example.ehotel.entities.CheckinRoom;
import com.example.ehotel.entities.CheckoutRoom;

public interface CheckService {

    public void createCheckin(CheckinRoom checkinRoom, String roomId);

    public void createCheckout(CheckoutRoom checkoutRoom, String roomId);
}
