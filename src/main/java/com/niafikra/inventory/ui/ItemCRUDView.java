package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.service.ItemService;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

import javax.annotation.PostConstruct;

import static com.niafikra.inventory.backend.service.ItemServiceImp.*;

@Route(layout = MainView.class)
public class ItemCRUDView extends VerticalLayout {

    private ItemService itemService;

    private ItemsProvider itemsProvider;
    private ConfigurableFilterDataProvider<Item,Void, ItemFilter> filterConfigurableProvider;

    private GridCrud<Item> itemGridCrud = new GridCrud<>(Item.class);
    private TextField codeFilterField;
    private TextField nameFilterField;

    private ItemFilter filter;

    public ItemCRUDView(ItemService itemService, ItemsProvider itemsProvider) {
        this.itemService = itemService;
        this.itemsProvider = itemsProvider;

        filter = new ItemFilter();
        filterConfigurableProvider
                = itemsProvider.withConfigurableFilter();
        filterConfigurableProvider.setFilter(filter);
    }


    @PostConstruct
    private void build() {

        // configuring itemGridCrud
        itemGridCrud.getCrudFormFactory().setUseBeanValidation(true);
        itemGridCrud.getGrid().removeColumnByKey("id");
        itemGridCrud.getGrid().setDataProvider(itemsProvider);

        // filter row
        HeaderRow filterRow = itemGridCrud.getGrid().appendHeaderRow();

        // code filter
        codeFilterField = new TextField();
        codeFilterField.addValueChangeListener(event -> {
            filter.setCode(event.getValue());
            filterConfigurableProvider.refreshAll();
        });
        codeFilterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterRow.getCell(itemGridCrud.getGrid().getColumnByKey("code"))
                .setComponent(codeFilterField);
        codeFilterField.setSizeFull();
        codeFilterField.setPlaceholder("Filter");

        // name filter
        nameFilterField = new TextField();
        nameFilterField.addValueChangeListener(event -> {
            filter.setName(event.getValue());
            filterConfigurableProvider.refreshAll();
        });
        nameFilterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterRow.getCell(itemGridCrud.getGrid().getColumnByKey("name"))
                .setComponent(nameFilterField);
        nameFilterField.setSizeFull();
        nameFilterField.setPlaceholder("Filter");

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
