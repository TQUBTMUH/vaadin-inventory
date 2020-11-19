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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

@Route("item-form")
public class ItemForm extends FormLayout {

    private ItemService itemService;
    private StockService stockService;

    TextField code = new TextField();
    TextField name = new TextField();

    Button save = new Button("Save");

    Binder<Item> binder = new BeanValidationBinder<>(Item.class);

    public ItemForm(ItemService itemService, StockService stockService) {
        this.itemService = itemService;
        this.stockService = stockService;

        addClassName("centered-content");

        // Code field
        code.setPlaceholder("Item Code");

        // name field
        name.setPlaceholder("Item Name");

        // Button configuration
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);

        save.addClickListener(click -> {
            try {
                // Create empty bean to store item details
                Item newItem = new Item();

                // Run validators and write the values to the bean
                binder.writeBean(newItem);

                // Save bean to backend
                itemService.save(newItem);

                // Save new item into stock
                Item savedItem = itemService.findById(newItem.getId());

                Stock newStock = new Stock(savedItem, 0);
                stockService.save(newStock);

                // create notification


                // clear fields
                clearFields();

            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });
        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        binder.bindInstanceFields(this);


        // Header
        Div header = new Div(new H3("Create new item"));

        add(header, code, name, save);
 }

    private void clearFields() {
        binder.readBean(null);
    }

}
