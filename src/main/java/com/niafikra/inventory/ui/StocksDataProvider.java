package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Stock;
import com.niafikra.inventory.backend.service.StockService;
import com.niafikra.inventory.backend.service.StockServiceImp;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import java.util.List;

import static com.niafikra.inventory.backend.service.StockServiceImp.*;

@Component
@Scope("prototype")
public class StocksDataProvider extends PageableDataProvider<Stock, StockFilter> {

    StockService stockService;

    public StocksDataProvider(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    protected Page<Stock> fetchFromBackEnd(Query<Stock, StockFilter> query, Pageable pageable) {
        return stockService.findAll(query.getFilter().orElse(new StockFilter()), pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return QuerySortOrder.desc("quantity").build();
    }

    @Override
    protected int sizeInBackEnd(Query<Stock, StockFilter> query) {
        return stockService.count(query.getFilter().orElse(new StockFilter())).intValue();
    }
}
