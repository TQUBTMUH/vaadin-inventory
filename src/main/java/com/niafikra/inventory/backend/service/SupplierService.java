package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.Supplier;

import java.util.List;

public interface SupplierService {
    public List<Supplier> findAll();

    public void save(Supplier newSupplier);

    public Supplier findById(Long theId);
}
