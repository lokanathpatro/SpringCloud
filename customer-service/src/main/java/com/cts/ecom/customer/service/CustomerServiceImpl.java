package com.cts.ecom.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cts.ecom.customer.model.Customer;
import com.cts.ecom.customer.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository CustomerRepository;

    @Override
    public Customer createCustomer(Customer Customer) {
        return CustomerRepository.save(Customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return CustomerRepository.findOne(id);
    }
    

    @Override
    public Customer editCustomer(Customer Customer) {
        return CustomerRepository.save(Customer);
    }

    @Override
    public void deleteCustomer(Customer Customer) {
        CustomerRepository.delete(Customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        CustomerRepository.delete(id);
    }

    @Override
    public List<Customer> getAllCustomers(int pageNumber, int pageSize) {
        return CustomerRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return CustomerRepository.findAll();
    }

    @Override
    public long countCustomers() {
        return CustomerRepository.count();
    }
}
