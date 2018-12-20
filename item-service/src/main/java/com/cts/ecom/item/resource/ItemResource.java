package com.cts.ecom.item.resource;

import java.net.URI;
import java.util.List;

import com.cts.ecom.item.model.ItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.ecom.item.model.Item;
import com.cts.ecom.item.service.ItemService;

@RestController
public class ItemResource {
	
	@Autowired
	private ItemService itemService;
	
	//private ItemRepository ItemRepository;
	
	@GetMapping("/items")
	public List<Item> getAllItems() {
		return itemService.getAllItems();
	}
	
	@GetMapping("/items/{id}")
	public Item retrieveItem(@PathVariable long id) {
		return itemService.getItem(id);
	}
	
	@PostMapping("/items")
	public ResponseEntity<Object> createItem(@RequestBody Item Item) {
		Item savedItem = itemService.createItem(Item);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedItem.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@GetMapping("/itemList")
	public ItemList getItemList() {
		ItemList itemList= new ItemList();
		itemList.setItems(itemService.getAllItems());
		return itemList;
	}

}
