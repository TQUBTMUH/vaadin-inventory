package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.PurchaseOrder;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderService {
    List<PurchaseOrder> findAll();

    void deleteById(Long theId);

    void save(PurchaseOrder purchaseOrder);

    Optional<PurchaseOrder> findById(Long theId);
}
