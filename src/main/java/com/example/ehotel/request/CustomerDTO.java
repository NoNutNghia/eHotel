package com.example.ehotel.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CustomerDTO {
    private String fullName;
    private String dateOfBirth;
    private String phoneNumber;
    private String identityNumber;
}
