package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.SupplierRepository;
import com.niafikra.inventory.backend.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImp implements SupplierService {

    SupplierRepository supplierRepository;

    public SupplierServiceImp(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier save(Supplier newSupplier) {
        Supplier supplier = supplierRepository.findByName(newSupplier.getName());

        if (supplier == null) {
            supplierRepository.save(newSupplier);
        } else {
            throw new RuntimeException("Supplier " + newSupplier.getName() + " already exists");
        }
        return newSupplier;
    }

    @Override
    public Supplier findById(Long theId) {
        Optional<Supplier> result = supplierRepository.findById(theId);

        Supplier theSupplier;
        if (result.isPresent()) {
            theSupplier = result.get();
        } else {
            throw new RuntimeException("Supplier with id of " + theId + " doesn't exist");
        }

        return theSupplier;
    }

    @Override
    public void delete(Supplier supplier) {
        supplierRepository.delete(supplier);
    }

    @Override
    public Supplier update(Supplier supplier) {
        supplierRepository.save(supplier);

        return supplier;
    }

    @Override
    public Page<Supplier> findAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    @Override
    public Long count() {
        return count();
    }
}

