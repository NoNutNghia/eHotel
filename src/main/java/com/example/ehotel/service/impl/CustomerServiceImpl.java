package com.example.ehotel.service.impl;

import com.example.ehotel.connectDB.ConnectDB;
import com.example.ehotel.model.Customer;
import com.example.ehotel.request.CustomerDTO;
import com.example.ehotel.service.CustomerService;
import org.bson.Document;

import java.util.UUID;

public class CustomerServiceImpl implements CustomerService {

    @Override
    public void createCustomer(CustomerDTO customerDTO) {

        var connect = ConnectDB.connectDatabase("customer");

        var customerDoc = new Document("_id", UUID.randomUUID().toString());

        customerDoc.append("fullName", customerDTO.getFullName());
        customerDoc.append("dateOfBirth", customerDTO.getDateOfBirth());
        customerDoc.append("phoneNumber", customerDTO.getPhoneNumber());
        customerDoc.append("identityNumber", customerDTO.getIdentityNumber());
        customerDoc.append("idRoom", "");
        customerDoc.append("checkinAt", "");
        customerDoc.append("checkoutAt", "");

        connect.insertOne(customerDoc);

        ConnectDB.closeConnect();
    }
}
