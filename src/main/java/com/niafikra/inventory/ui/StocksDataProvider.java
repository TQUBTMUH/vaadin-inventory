package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Stock;
import com.niafikra.inventory.backend.service.StockService;
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
public class StocksDataProvider extends PageableDataProvider<Stock, Void> {

    StockService stockService;

    public StocksDataProvider(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    protected Page<Stock> fetchFromBackEnd(Query<Stock, Void> query, Pageable pageable) {
        return stockService.findAll(pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return QuerySortOrder.desc("quantity").build();
    }

    @Override
    protected int sizeInBackEnd(Query<Stock, Void> query) {
        return stockService.count().intValue();
    }
}
