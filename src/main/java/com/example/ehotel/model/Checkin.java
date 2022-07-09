package com.example.ehotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Checkin {
    private String _id;
    private String timeCheckin;
    private String customerId;
    private String roomId;
}
