package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Supplier;
import com.niafikra.inventory.backend.service.SupplierService;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import java.util.List;

@Component
@Scope("prototype")
public class SupplierProvider extends PageableDataProvider<Supplier, Void> {

    private SupplierService supplierService;

    public SupplierProvider(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    protected Page<Supplier> fetchFromBackEnd(Query<Supplier, Void> query, Pageable pageable) {
        return supplierService.findAll(pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return QuerySortOrder.asc("name").build();
    }

    @Override
    protected int sizeInBackEnd(Query<Supplier, Void> query) {
        return supplierService.count().intValue();
    }
}
