package com.example.ehotel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@NoArgsConstructor
@AllArgsConstructor
@Data

@BsonDiscriminator
public class CheckoutRoom {
    private String _id;
    private String timeCheckout;
    private String customerId;
}
