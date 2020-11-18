package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.ItemRepository;
import com.niafikra.inventory.backend.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImp implements ItemService{
    @Autowired
    private ItemRepository itemRepository;

    private StockService stockService;

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public void save(Item newItem) {
        // check if Item exists on Item DB
        Item myItem = itemRepository.findByName(newItem.getName());
        Item itemByCode = itemRepository.findByCode(newItem.getCode());

        if (myItem == null && itemByCode == null) {
            itemRepository.save(newItem);
        } else {
            // Item exists of Item DB
            throw new RuntimeException("Item " + newItem.getName() + " with Code " +
                    newItem.getCode() + " already exists");
        }
    }

    @Override
    public Item findById(Long theId) {
        Optional<Item> result = itemRepository.findById(theId);

        Item myItem = null;
        if (result.isPresent()) {
            myItem = result.get();
        } else {
            // didn't find the Item
            throw new RuntimeException("Didn't find the Item with id = " + theId);
        }
        return myItem;
    }

}

