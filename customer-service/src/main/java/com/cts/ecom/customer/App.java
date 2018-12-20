package com.cts.ecom.customer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableDiscoveryClient
public class App {

	private static final Logger LOG = LoggerFactory.getLogger("JCG");

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
