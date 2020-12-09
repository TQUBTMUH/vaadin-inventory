package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.service.ItemService;
import com.niafikra.inventory.backend.service.ItemServiceImp;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

import javax.annotation.PostConstruct;

@Route(layout = MainView.class)
public class ItemCRUDView extends VerticalLayout {

    private ItemService itemService;

    private ItemsProvider itemsProvider;
    private ConfigurableFilterDataProvider<Item,Void, ItemServiceImp.ItemFilter> filterConfigurableProvider;

    GridCrud<Item> itemGridCrud = new GridCrud<>(Item.class);

    ItemServiceImp.ItemFilter filter ;

    public ItemCRUDView(ItemService itemService, ItemsProvider itemsProvider) {
        this.itemService = itemService;
        this.itemsProvider = itemsProvider;

        filter = new ItemServiceImp.ItemFilter();
        filterConfigurableProvider
                = itemsProvider.withConfigurableFilter();
        filterConfigurableProvider.setFilter(filter);
    }


    @PostConstruct
    private void build() {

        TextField codeFilterField = new TextField();
        codeFilterField.addValueChangeListener(event -> {
            filter.setCode(event.getValue());
            filterConfigurableProvider.refreshAll();
        });
        TextField nameFilterField = new TextField();
        nameFilterField.addValueChangeListener(event -> {
            filter.setName(event.getValue());
            filterConfigurableProvider.refreshAll();
        });

        add(new HorizontalLayout(codeFilterField, nameFilterField));

        // configuring itemGridCrud
        itemGridCrud.getCrudFormFactory().setUseBeanValidation(true);
        itemGridCrud.getGrid().removeColumnByKey("id");

        // customizing fields
        itemGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "name", "code");
        itemGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "name", "code");
        itemGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "name", "code");
        itemGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "name", "code");

//        itemGridCrud.setFindAllOperation(() -> itemService.findAll());

        itemGridCrud.setFindAllOperation(filterConfigurableProvider);
        itemGridCrud.setAddOperation(item -> itemService.save(item));
        itemGridCrud.setUpdateOperation(item -> itemService.update(item));
        itemGridCrud.setDeleteOperation(item -> itemService.delete(item));

        add(itemGridCrud);
    }
}
