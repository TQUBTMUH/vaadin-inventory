package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.Stock;

import java.util.List;

public interface StockService {
    public List<Stock> findAll();

    // auto increment item quantity with value from received PO items
    public void customeStockUpdate(Long theId);

    public void deleteById(Long theId);

    public void save(Stock stock);
}
