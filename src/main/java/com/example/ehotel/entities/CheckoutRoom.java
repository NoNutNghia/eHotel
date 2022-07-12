package com.example.ehotel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CheckoutRoom {
    private String _id;
    private String timeCheckout;
    private String customerId;
}
