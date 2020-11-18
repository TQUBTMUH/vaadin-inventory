package com.niafikra.inventory.backend.dao;


import com.niafikra.inventory.backend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByItem_Id(Long theId);
}
