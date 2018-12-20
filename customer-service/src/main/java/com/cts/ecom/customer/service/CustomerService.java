package com.cts.ecom.customer.service;

import java.util.List;

import com.cts.ecom.customer.model.Customer;

	public interface CustomerService {

	    Customer createCustomer(Customer Customer);

	    Customer getCustomer(Long customer_id);

	    Customer editCustomer(Customer Customer);

	    void deleteCustomer(Customer Customer);

	    void deleteCustomer(Long customer_id);

	    List<Customer> getAllCustomers(int pageNumber, int pageSize);

	    List<Customer> getAllCustomers();

	    long countCustomers();
	}
