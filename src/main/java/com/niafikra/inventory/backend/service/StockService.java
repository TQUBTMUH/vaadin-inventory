package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockService {
    List<Stock> findAll();

    // auto increment item quantity with value from received PO items
    void customeStockUpdate(Long theId);

    void deleteById(Long theId);

    void save(Stock stock);

    Page<Stock> findAll(Pageable pageable);

    Long count();
}
