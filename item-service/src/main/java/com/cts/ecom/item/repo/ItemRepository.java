package com.cts.ecom.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.ecom.item.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
