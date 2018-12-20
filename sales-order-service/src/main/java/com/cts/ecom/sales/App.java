package com.cts.ecom.sales;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.cts.ecom.sales.model.Customer_SOS;
import com.cts.ecom.sales.repo.CustomerSosRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableBinding(Sink.class)
@SpringBootApplication
@EnableDiscoveryClient
public class App {

private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	@Autowired
	CustomerSosRepository customerSosRepository;

	@StreamListener(Sink.INPUT)
	public void processRegisterEmployees(String customer) {
		System.out.println("Customer Registered to Customer SOS Table " + customer);
		
		ObjectMapper mapper = new ObjectMapper();
		LOG.info("before try");
		
		try {
			System.out.println();
			JsonNode customerObj = mapper.readTree(customer);
			System.out.println("first name"+customerObj.get("first_name").asText());
			
			Customer_SOS customerSOS = new Customer_SOS();
			customerSOS.setCust_id(customerObj.get("id").asLong());
			customerSOS.setCust_first_name(customerObj.get("first_name").asText());
			customerSOS.setCust_last_name(customerObj.get("last_name").asText());
			customerSOS.setCust_email(customerObj.get("email").asText());
			customerSosRepository.save(customerSOS);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//customeSOS=customerSosRepository.save(customer);
	}
}
