package com.example.ehotel.service.impl;

import com.example.ehotel.connectDB.ConnectDB;
import com.example.ehotel.model.Customer;
import com.example.ehotel.request.CustomerDTO;
import com.example.ehotel.service.CustomerService;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class CustomerServiceImpl implements CustomerService {

    @Override
    public String createCustomer(CustomerDTO customerDTO) {

        var connect = ConnectDB.connectDatabase("customer");

        String idCustomer = UUID.randomUUID().toString();

        var customerDoc = new Document("_id", idCustomer);

        customerDoc.append("fullName", customerDTO.getFullName());
        customerDoc.append("dateOfBirth", customerDTO.getDateOfBirth());
        customerDoc.append("phoneNumber", customerDTO.getPhoneNumber());
        customerDoc.append("idRoom", "");
        customerDoc.append("identityNumber", customerDTO.getIdentityNumber());
        customerDoc.append("reserveAt", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        customerDoc.append("checkinAt", "");
        customerDoc.append("checkoutAt", "");

        connect.insertOne(customerDoc);

        ConnectDB.closeConnect();

        return idCustomer;
    }

    @Override
    public Customer getCustomerById(String id) {
        var connect = ConnectDB.connectDatabase("customer");

        Bson filer = Filters.eq("_id", id);

        Document doc = connect.find(filer).first();

        Customer customer = returnObject(doc);

        ConnectDB.closeConnect();

        return customer;

    }

    private Customer returnObject(Document document) {
        Customer customer = new Customer();
        customer.set_id(document.get("_id").toString());
        customer.setFullName(document.get("fullName").toString());
        customer.setDateOfBirth(document.get("dateOfBirth").toString());
        customer.setPhoneNumber(document.get("phoneNumber").toString());
        customer.setIdRoom(document.get("idRoom").toString());
        customer.setCheckinAt(document.get("checkinAt").toString());
        customer.setReserveAt(document.get("reserveAt").toString());
        customer.setIdentityNumber(document.get("identityNumber").toString());
        customer.setCheckoutAt(document.get("checkoutAt").toString());

        return customer;
    }
}
