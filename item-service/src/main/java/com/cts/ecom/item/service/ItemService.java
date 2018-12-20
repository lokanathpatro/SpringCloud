package com.cts.ecom.item.service;

import java.util.List;

import com.cts.ecom.item.model.Item;

	public interface ItemService {

	    Item createItem(Item item);

	    Item getItem(Long id);

	    Item editItem( Item item);

	    void deleteItem(Item item);

	    void deleteItem(Long id);

	    List<Item> getAllItems(int pageNumber, int pageSize);

	    List<Item> getAllItems();

	    long countItems();
	}
