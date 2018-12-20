package com.cts.ecom.item.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cts.ecom.item.model.Item;
import com.cts.ecom.item.repo.ItemRepository;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item createItem(Item Item) {
        return itemRepository.save(Item);
    }

    @Override
    public Item getItem(Long id) {
        return itemRepository.findOne(id);
    }
    

    @Override
    public Item editItem(Item Item) {
        return itemRepository.save(Item);
    }

    @Override
    public void deleteItem(Item Item) {
        itemRepository.delete(Item);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.delete(id);
    }

    @Override
    public List<Item> getAllItems(int pageNumber, int pageSize) {
        return itemRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public long countItems() {
        return itemRepository.count();
    }
}
