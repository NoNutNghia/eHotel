package com.example.ehotel.service;

import com.example.ehotel.model.Customer;
import com.example.ehotel.request.CustomerDTO;

public interface CustomerService {

    public String createCustomer(CustomerDTO customerDTO);

    public Customer getCustomerById(String id);
}
