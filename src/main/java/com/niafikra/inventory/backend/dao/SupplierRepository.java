package com.niafikra.inventory.backend.dao;

import com.niafikra.inventory.backend.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);

    Long countAllByName(String name);
    Page<Supplier> findByNameContaining(String name, Pageable pageable);
}
