package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    Item save(Item newItem);

    Item findById(Long theid);

    Item update(Item newItem);

    void delete(Item item);

}
