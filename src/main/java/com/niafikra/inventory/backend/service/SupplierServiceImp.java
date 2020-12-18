package com.niafikra.inventory.backend.service;

import com.niafikra.inventory.backend.dao.SupplierRepository;
import com.niafikra.inventory.backend.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

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
    public Page<Supplier> findAll(SupplierFilter supplierFilter, Pageable pageable) {
        if (isEmpty(supplierFilter.name)) {
            return supplierRepository.findAll(pageable);
        } else {
            return supplierRepository.findByNameContaining(supplierFilter.getName(), pageable);
        }
    }

    @Override
    public Long count() {
        return count();
    }

    @Override
    public Long count(SupplierFilter supplierFilter) {
        if (isEmpty(supplierFilter)) {
            return  supplierRepository.count();
        } else {
            return supplierRepository.countAllByName(supplierFilter.getName());
        }
    }


    public static class SupplierFilter {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

