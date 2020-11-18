package com.niafikra.inventory.backend.dao;

import com.niafikra.inventory.backend.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    public Supplier findByName(String name);
}
