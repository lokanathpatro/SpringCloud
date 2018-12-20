package com.cts.ecom.sales.model;

import java.util.Date;
import java.util.List;

public class SalesRequest {
	
	//Order Description, Order Date, customer id, list of item names
	
	String Order_Description;//please dispatch on weekend
	String order_date;//07/12/2018
	Long customer_id;//2000
	List<String> itemNames;//rolex watch
	
	public String getOrder_Description() {
		return Order_Description;
	}
	public void setOrder_Description(String order_Description) {
		Order_Description = order_Description;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	public List<String> getItemNames() {
		return itemNames;
	}
	public void setItemNames(List<String> itemNames) {
		this.itemNames = itemNames;
	}
	

}
