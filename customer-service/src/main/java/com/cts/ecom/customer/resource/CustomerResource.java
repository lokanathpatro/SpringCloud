package com.cts.ecom.customer.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.ecom.customer.model.Customer;
import com.cts.ecom.customer.service.CustomerRegistrationSource;
import com.cts.ecom.customer.service.CustomerService;

@RestController
@EnableBinding(CustomerRegistrationSource.class)
public class CustomerResource {
	
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/customers/{id}")
	public Customer retrieveStudent(@PathVariable long id) {
		return customerService.getCustomer(id);
	}
	
	@Autowired
	CustomerRegistrationSource customerRegistrationSource;
	
	@PostMapping("/customers")
	public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.createCustomer(customer);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCustomer.getId()).toUri();
		customerRegistrationSource.customerRegistration().send(MessageBuilder.withPayload(customer).build());
		return ResponseEntity.created(location).build();

	}

}
