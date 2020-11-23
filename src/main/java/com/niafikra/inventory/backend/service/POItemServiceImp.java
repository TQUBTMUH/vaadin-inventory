package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.POItemRepository;
import com.niafikra.inventory.backend.entity.POItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POItemServiceImp implements POItemService {

    POItemRepository poItemRepository;

    public POItemServiceImp(POItemRepository poItemRepository) {
        this.poItemRepository = poItemRepository;
    }

    @Override
    public List<POItem> findAll() {
        return poItemRepository.findAll();
    }

    @Override
    public void deleteById(Long theId) {
        poItemRepository.deleteById(theId);
    }

    @Override
    public void save(POItem poItem) {
        poItemRepository.save(poItem);
    }
}
