package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.PurchaseOrderRepository;
import com.niafikra.inventory.backend.entity.PurchaseOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderServiceImp implements PurchaseOrderService {

    PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderServiceImp(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public void deleteById(Long theId) {
        purchaseOrderRepository.deleteById(theId);
    }

    @Override
    public void save(PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public Optional<PurchaseOrder> findById(Long theId) {
        return purchaseOrderRepository.findById(theId);
    }

}
