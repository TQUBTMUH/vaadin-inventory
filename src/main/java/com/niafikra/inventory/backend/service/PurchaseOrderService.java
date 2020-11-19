package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.PurchaseOrder;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderService {
    public List<PurchaseOrder> findAll();

    public void deleteById(Long theId);

    public void save(PurchaseOrder purchaseOrder);

    public Optional<PurchaseOrder> findById(Long theId);
}
