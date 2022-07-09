package com.example.ehotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Checkout {
    private String _id;
    private String timeCheckout;
    private String customerId;
    private String roomId;

}
