package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.PurchaseOrderRepository;
import com.niafikra.inventory.backend.entity.PurchaseOrder;
import com.niafikra.inventory.ui.OffsetBasedPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

//    @Override
//    public Stream<PurchaseOrder> findAll(int offset, int limit) {
//        return purchaseOrderRepository.findAll(new OffsetBasedPageRequest(offset,limit)).stream();
//    }

    @Override
    public Page<PurchaseOrder> findAll(Pageable pageable) {
        return purchaseOrderRepository.findAll(pageable);
    }

    @Override
    public Long count() {
        return purchaseOrderRepository.count();
    }

}
