package com.cts.ecom.customer.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomerRegistrationSource {

	@Output("customerRegistrationChannel")
	MessageChannel customerRegistration();
}
