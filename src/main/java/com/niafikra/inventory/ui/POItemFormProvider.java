package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.service.ItemService;
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
public class POItemFormProvider extends PageableDataProvider<Item, String> {

    private ItemService itemService;

    public POItemFormProvider(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected Page<Item> fetchFromBackEnd(Query<Item, String> query, Pageable pageable) {
        return itemService.findAll(
                query.getFilter().orElse(null),
                pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return QuerySortOrder.asc("name").build();
    }

    @Override
    protected int sizeInBackEnd(Query<Item, String> query) {
        return itemService.count(query.getFilter().orElse(null)).intValue();
    }
}
