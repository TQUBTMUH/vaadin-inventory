package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.PurchaseOrderRepository;
import com.niafikra.inventory.backend.dao.StockRepository;
import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.PurchaseOrder;
import com.niafikra.inventory.backend.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImp implements StockService {

    StockRepository stockRepository;

    PurchaseOrderRepository purchaseOrderRepository;

    // used down below
    PurchaseOrder myPurchaserOrder;

    public StockServiceImp(StockRepository stockRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.stockRepository = stockRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public void deleteById(Long theId) {
        stockRepository.deleteById(theId);
    }

    @Override
    public void save(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public void customeStockUpdate(Long theId) {
        // get the item in PO with given Id
        myPurchaserOrder = purchaseOrderRepository.getOne(theId);

        // find stock record using item Id
        Item foundItem = myPurchaserOrder.getItem();
        Stock myStock = stockRepository.findByItem_Id(foundItem.getId());

//        Long foundItemId = purchaseOrderRepository.findByItemId(myPurchaserOrder.getId());
//        Stock myStock = stockRepository.findByItem_Id(foundItemId);

        // get stock item's ID
        int myStockQuantity = myStock.getQuantity();

        // update stock quantity
        myStockQuantity += myPurchaserOrder.getQuantity();

        // update stock quantity of Stock Item
        myStock.setQuantity(myStockQuantity);
        stockRepository.save(myStock);
    }


}

