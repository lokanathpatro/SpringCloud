package com.cts.ecom.sales.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sales_order {
	
	//id, order_date, cust_id, order_desc, total_price
	
	@Id
	Long id;
	String order_date;
	Long cust_id;
	String order_desc;
	Long total_price;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public Long getCust_id() {
		return cust_id;
	}
	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}
	public String getOrder_desc() {
		return order_desc;
	}
	public void setOrder_desc(String order_desc) {
		this.order_desc = order_desc;
	}
	public Long getTotal_price() {
		return total_price;
	}
	public void setTotal_price(Long total_price) {
		this.total_price = total_price;
	}
	
}
