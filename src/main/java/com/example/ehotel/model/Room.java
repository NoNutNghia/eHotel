package com.example.ehotel.model;

import com.example.ehotel.entities.CheckinRoom;
import com.example.ehotel.entities.CheckoutRoom;
import com.example.ehotel.entities.RegisterCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Room {
    private String _id;
    private String name;
    private Double price;
    private Integer beds;
    private String img;
    private List<CheckinRoom> checkinRoom = new ArrayList<>();
    private List<CheckoutRoom> checkoutRoom = new ArrayList<>();
    private Double pointRating;
    private String status;
    private String registerCustomer;
    private List<Double> rating = new ArrayList<>();

}
