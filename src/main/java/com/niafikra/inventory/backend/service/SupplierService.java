package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> findAll();

    Supplier save(Supplier newSupplier);

    Supplier findById(Long theId);

    void delete(Supplier supplier);

    Supplier update(Supplier supplier);

}
