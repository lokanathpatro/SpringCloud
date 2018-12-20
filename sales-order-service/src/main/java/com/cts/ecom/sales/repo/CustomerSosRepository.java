package com.cts.ecom.sales.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.ecom.sales.model.Customer_SOS;

@Repository
public interface CustomerSosRepository extends JpaRepository<Customer_SOS, Long> {
	
}
