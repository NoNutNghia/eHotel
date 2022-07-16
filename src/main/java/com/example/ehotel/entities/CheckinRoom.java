package com.example.ehotel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;


@Data
@NoArgsConstructor
@AllArgsConstructor

@BsonDiscriminator
public class CheckinRoom {
    private String _id;
    private String timeCheckin;
    private String customerId;

}
