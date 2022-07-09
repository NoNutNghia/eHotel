package com.example.ehotel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CheckinRoom {
    private String id;
    private String timeCheckin;
    private String customerId;

}
