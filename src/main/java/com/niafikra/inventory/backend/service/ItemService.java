package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.service.ItemServiceImp.ItemFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    Item save(Item newItem);

    Item findById(Long theid);

    Item update(Item newItem);

    void delete(Item item);

    Long count(String itemFilter);

    Page<Item> findAll(String itemFilter,Pageable pageable);

    Page<Item> findAll(ItemFilter itemFilter, Pageable pageable);

    Long count(ItemFilter itemFilter);
}
