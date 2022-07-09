package com.example.ehotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private String _id;
    private String fullName;
    private String dateOfBirth;
    private String phoneNumber;
    private String identityNumber;
    private String checkInAt;
    private String checkOutAt;

}
