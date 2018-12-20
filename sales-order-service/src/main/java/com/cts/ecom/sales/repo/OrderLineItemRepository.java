package com.cts.ecom.sales.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.ecom.sales.model.Customer_SOS;
import com.cts.ecom.sales.model.Order_line_item;
import com.cts.ecom.sales.model.Sales_order;

@Repository
public interface OrderLineItemRepository extends JpaRepository<Order_line_item, Long> {
	
}
