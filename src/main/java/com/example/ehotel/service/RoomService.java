package com.example.ehotel.service;

import com.example.ehotel.model.Customer;
import com.example.ehotel.model.Room;
import com.example.ehotel.request.RoomDTO;

import java.util.List;

public interface RoomService {

    public void reserveRoom(String customerId, String roomId);

    public void checkinRoom(String roomId, String customerId);

    public void checkoutRoom(String roomId, String customerId);

    public void updateRoom(RoomDTO roomDTO, String roomId);

    public Room getRoomById(String roomId);

    public List<Room> findAllRoom();

}
