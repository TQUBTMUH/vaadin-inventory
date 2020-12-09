package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.service.ItemService;
import com.niafikra.inventory.backend.service.ItemServiceImp.ItemFilter;
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
public class ItemsProvider extends PageableDataProvider<Item, ItemFilter> {

    private final ItemService itemService;

    public ItemsProvider(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected Page<Item> fetchFromBackEnd(Query<Item, ItemFilter> query, Pageable pageable) {

        return itemService.findAll(
                query.getFilter().orElse(new ItemFilter()),
                pageable
        );
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return QuerySortOrder.asc("name").build();
    }

    @Override
    protected int sizeInBackEnd(Query<Item, ItemFilter> query) {

        return itemService.count(
                query.getFilter().orElse(new ItemFilter())
        ).intValue();
    }
}
