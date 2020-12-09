package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.ItemRepository;
import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class ItemServiceImp implements ItemService {

    private ItemRepository itemRepository;

    private StockService stockService;

    public ItemServiceImp(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item save(Item newItem) {
        // check if Item exists on Item DB
        Item myItem = itemRepository.findByName(newItem.getName());
        Item itemByCode = itemRepository.findByCode(newItem.getCode());

        if (myItem == null && itemByCode == null) {
            itemRepository.save(newItem);

            // create new item with quantity = 0 in stock database
            Stock newStock = new Stock(newItem, 0);
            stockService.save(newStock);
        } else {
            // Item exists of Item DB
            throw new RuntimeException("Item " + newItem.getName() + " with Code " +
                    newItem.getCode() + " already exists");
        }

        return newItem;
    }

    @Override
    public Item findById(Long theId) {
        Optional<Item> result = itemRepository.findById(theId);

        Item myItem = null;
        if (result.isPresent()) {
            myItem = result.get();
        } else {
            // didn't find the Item
            throw new RuntimeException("Didn't find the Item with id = " + theId);
        }
        return myItem;
    }

    @Override
    public Item update(Item newItem) {
        itemRepository.save(newItem);
        return newItem;
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public Long count(String itemFilter) {
        if (itemFilter == null)
            return itemRepository.count();
        else return itemRepository.countAllByNameStartingWith(itemFilter);
    }

    @Override
    public Page<Item> findAll(String itemFilter, Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Page<Item> findAll(ItemFilter itemFilter, Pageable pageable) {
        if (isEmpty(itemFilter.code) && isEmpty(itemFilter.name)) {
            return itemRepository.findAll(pageable);
        } else if (isEmpty(itemFilter.code) && !isEmpty(itemFilter.name)) {
            return itemRepository.findAllByName(itemFilter.getName(),pageable);
        } else if (!isEmpty(itemFilter.code) && isEmpty(itemFilter.name)) {
            return itemRepository.findAllByCode(itemFilter.getCode(),pageable);
        }else {
            return itemRepository.findAllByCodeAndName(itemFilter.getCode(),itemFilter.getName(),pageable);
        }
    }

    @Override
    public Long count(ItemFilter itemFilter) {
        if (isEmpty(itemFilter.code) && isEmpty(itemFilter.name)) {
            return itemRepository.count();
        } else if (isEmpty(itemFilter.code) && !isEmpty(itemFilter.name)) {
            return itemRepository.countAllByName(itemFilter.getName());
        } else if (!isEmpty(itemFilter.code) && isEmpty(itemFilter.name)) {
            return itemRepository.countAllByCode(itemFilter.getCode());
        }else {
            return itemRepository.countAllByCodeAndName(itemFilter.getCode(),itemFilter.getName());
        }
    }

    public static class ItemFilter {
        private String name;
        private String code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}

