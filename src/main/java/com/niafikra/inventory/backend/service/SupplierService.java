package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {
    List<Supplier> findAll();

    Supplier save(Supplier newSupplier);

    Supplier findById(Long theId);

    void delete(Supplier supplier);

    Supplier update(Supplier supplier);

    Page<Supplier> findAll(Pageable pageable);

    Long count();

}
