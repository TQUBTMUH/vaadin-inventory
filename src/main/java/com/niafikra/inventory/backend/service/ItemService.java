package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    public void save(Item newItem);

    public Item findById(Long theid);
}
