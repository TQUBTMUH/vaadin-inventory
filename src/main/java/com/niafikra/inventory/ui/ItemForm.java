package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.Stock;
import com.niafikra.inventory.backend.service.ItemService;
import com.niafikra.inventory.backend.service.StockService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.Arrays;
import java.util.Collection;

@Route(layout = MainView.class)
public class ItemForm extends VerticalLayout {

    private ItemService itemService;

    GridCrud<Item> itemGridCrud = new GridCrud<>(Item.class);

    public ItemForm(ItemService itemService) {
        this.itemService = itemService;

        // configuring itemGridCrud
        itemGridCrud.getCrudFormFactory().setUseBeanValidation(true);
        itemGridCrud.getGrid().removeColumnByKey("id");

        // customizing fields
        itemGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "name", "code");
        itemGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "name", "code");
        itemGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "name", "code");
        itemGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "name", "code");

        // crud listener
        itemGridCrud.setCrudListener(new CrudListener<Item>() {
            @Override
            public Collection<Item> findAll() {
                return itemService.findAll();
            }

            @Override
            public Item add(Item newItem) {
                return itemService.save(newItem);
            }

            @Override
            public Item update(Item item) {
                return itemService.update(item);
            }

            @Override
            public void delete(Item item) {
                itemService.delete(item);
            }
        });


        add(itemGridCrud);
    }
}
