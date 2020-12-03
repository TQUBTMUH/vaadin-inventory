package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface PurchaseOrderService {
    public List<PurchaseOrder> findAll();

    public void deleteById(Long theId);

    public void save(PurchaseOrder purchaseOrder);

    public Optional<PurchaseOrder> findById(Long theId);

//    Stream<PurchaseOrder> findAll(int offset, int limit);

    Page<PurchaseOrder> findAll(Pageable pageable);

    Long count();
}
