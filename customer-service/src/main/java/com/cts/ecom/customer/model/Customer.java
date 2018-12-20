package com.cts.ecom.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
	 	@Id
	    @GeneratedValue
	  
	    private Long customer_id;
	 	private String email;
	    private String first_name;
	    private String last_name;

	    public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getLast_name() {
			return last_name;
		}

		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}

		public Customer() {
	    }

	    public Customer(String first_name, String last_name, String email) {
	        this.first_name = first_name;
	        this.last_name = last_name; 
	        this.email = email;
	    }

	    public Long getId() {
	        return customer_id;
	    }

	    public void setId(Long id) {
	        this.customer_id = id;
	    }

	    public String getFirst_name() {
	        return first_name;
	    }

	    public void setFirst_name(String first_name) {
	        this.first_name = first_name;
	    }

	    @Override
	    public String toString() {
	        return String.format("Customer{id=%d, first_name='%s', last_name=%s , email=%s}", customer_id, first_name, last_name, email);
	    }
	}
