package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.ItemRepository;
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

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class StockServiceImp implements StockService {

    private StockRepository stockRepository;
    private PurchaseOrderRepository purchaseOrderRepository;
    private PurchaseOrderService purchaseOrderService;
    private ItemRepository itemRepository;

    public StockServiceImp(StockRepository stockRepository,
                           PurchaseOrderRepository purchaseOrderRepository,
                           PurchaseOrderService purchaseOrderService, ItemRepository itemRepository) {
        this.stockRepository = stockRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderService = purchaseOrderService;
        this.itemRepository = itemRepository;
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
    public Page<Stock> findAll(StockFilter filter, Pageable pageable) {
        if (isEmpty(filter.item) && isEmpty(filter.quantity)) {
            return stockRepository.findAll(pageable);
        } else if (isEmpty(filter.item) && !isEmpty(filter.quantity)) {
            return stockRepository.findByQuantityContaining((filter.getQuantity()), pageable);
        } else if (!isEmpty(filter.item) && isEmpty(filter.quantity)) {
            return stockRepository.findByItemContaining(filter.getItem(), pageable);
        } else {
            return stockRepository.findByItemAndQuantityContaining(filter.getItem(),
                    filter.getQuantity(), pageable);
        }
    }

    @Override
    public Long count(StockFilter stockFilter) {
        if (isEmpty(stockFilter.item) && isEmpty(stockFilter.quantity)) {
            return stockRepository.count();
        } else if (isEmpty(stockFilter.item) && !(isEmpty(stockFilter.quantity))) {
            return stockRepository.countAllByQuantity(stockFilter.getQuantity());
        } else if (!isEmpty(stockFilter.item) && isEmpty(stockFilter.quantity)) {
            return stockRepository.countAllByItem(stockFilter.getItem());
        } else {
            return stockRepository.countAllByItemAndQuantity(stockFilter.getItem(), stockFilter.getQuantity());
        }
    }

//    @Override
//    public List<Stock> findAll(StockFilter filter) {
//        if (isEmpty(filter.item) && isEmpty(filter.quantity)) {
//            return stockRepository.findAll();
//        } else if (isEmpty(filter.item) && !isEmpty(filter.quantity)) {
//            return stockRepository.findByQuantityContaining((filter.getQuantity()));
//        } else if (!isEmpty(filter.item) && isEmpty(filter.quantity)) {
//            return stockRepository.findByItemContaining(filter.getItem());
//        }else {
//            return stockRepository.findByItemAndQuantityContaining(filter.getItem(),
//                    filter.getQuantity());
//        }
//    }

    @Override
    public Long count() {
        return stockRepository.count();
    }

    @Override
    public Item findByItemName(String name) {
        Item item = itemRepository.findByName(name);
        return item;
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
    }

    public static class StockFilter {
        private Item item;
        private Integer quantity;

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }


}

