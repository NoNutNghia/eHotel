package com.example.ehotel.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RoomDTO {
    private String name;
    private Double price;
    private Integer beds;
    private String status;
}
