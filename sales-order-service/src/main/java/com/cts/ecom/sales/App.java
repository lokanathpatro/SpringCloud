package com.cts.ecom.sales;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.cts.ecom.customer.model.Customer;
import com.cts.ecom.sales.model.Customer_SOS;
import com.cts.ecom.sales.repo.CustomerSosRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@EnableBinding(Sink.class)
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class App {

private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	@Autowired
	CustomerSosRepository customerSosRepository;

	@StreamListener(Sink.INPUT)
	public void processRegisterEmployees(Customer customer)  {
		System.out.println("Customer Registered to Customer SOS Table " + customer.toString());
		LOG.info("before try");
			Customer_SOS customerSOS = new Customer_SOS();
			customerSOS.setCust_id(customer.getId());
			customerSOS.setCust_email(customer.getEmail());
			customerSOS.setCust_first_name(customer.getFirst_name());
			customerSOS.setCust_last_name(customer.getLast_name());
			customerSosRepository.save(customerSOS);
	}
}
