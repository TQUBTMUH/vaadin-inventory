package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.POItem;

import java.util.List;

public interface POItemService {
    List<POItem> findAll();

    void deleteById(Long theId);

    void save(POItem poItem);
}
