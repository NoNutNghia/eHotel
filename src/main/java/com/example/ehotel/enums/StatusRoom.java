package com.example.ehotel.enums;

import lombok.Getter;

@Getter
public enum StatusRoom {

    AVAILABLE("available"),
    MAINTAIN("maintain"),
    IN_USE("in-use"),
    REGISTER("register");

    private final String statusRoom;

    StatusRoom(String statusRoom) {
        this.statusRoom = statusRoom;
    }
}
