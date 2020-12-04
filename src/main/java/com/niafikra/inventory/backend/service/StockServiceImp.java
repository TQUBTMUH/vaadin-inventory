package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.PurchaseOrderRepository;
import com.niafikra.inventory.backend.dao.StockRepository;
import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.POItem;
import com.niafikra.inventory.backend.entity.PurchaseOrder;
import com.niafikra.inventory.backend.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImp implements StockService {

    StockRepository stockRepository;

    PurchaseOrderRepository purchaseOrderRepository;

    PurchaseOrderService purchaseOrderService;

    public StockServiceImp(StockRepository stockRepository,
                           PurchaseOrderRepository purchaseOrderRepository,
                           PurchaseOrderService purchaseOrderService) {
        this.stockRepository = stockRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderService = purchaseOrderService;
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
    public Page<Stock> findAll(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }

    @Override
    public Long count() {
        return stockRepository.count();
    }

    @Override
    public void customeStockUpdate(Long theId) {
        // get the item in PO with given Id
//        myPurchaserOrder = purchaseOrderRepository.getOne(theId);
        PurchaseOrder myPurchaserOrder = purchaseOrderService.findById(theId).get();
//                .orElseThrow(() -> new IllegalArgumentException("There is no PO with ID "+theId));

        // find stock record using item Id
        List<POItem> poItems = myPurchaserOrder.getItems();

        for (POItem poItem : poItems) {
            Stock myStock = stockRepository.findByItem_Id(poItem.getItem().getId());

            if (myStock == null) {
                // If item isn't available in stock table
                Stock newStock = new Stock(poItem.getItem(), 0);

                // get stock poItem's ID
                int myStockQuantity = newStock.getQuantity();

                // update stock quantity
                myStockQuantity += poItem.getQuantity();

                // update stock quantity of Stock Item
                newStock.setQuantity(myStockQuantity);
                stockRepository.save(newStock);
            } else {
                // get stock in stock table poItem's ID
                int myStockQuantity = myStock.getQuantity();

                // update stock quantity
                myStockQuantity += poItem.getQuantity();

                // update stock quantity of Stock Item
                myStock.setQuantity(myStockQuantity);
                stockRepository.save(myStock);
            }
        }

//        Long foundItemId = purchaseOrderRepository.findByItemId(myPurchaserOrder.getId());
//        Stock myStock = stockRepository.findByItem_Id(foundItemId);
    }


}

