package com.example.ehotel.model;

import com.example.ehotel.entities.CheckinRoom;
import com.example.ehotel.entities.CheckoutRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room {
    private String _id;
    private String name;
    private Double price;
    private Integer beds;
    private String img;
    private List<CheckinRoom> checkinRoom = new ArrayList<>();
    private List<CheckoutRoom> checkoutRoom = new ArrayList<>();
    private Double starRating;
    private Boolean availability;
    private List<Integer> rating = new ArrayList<>();


}
