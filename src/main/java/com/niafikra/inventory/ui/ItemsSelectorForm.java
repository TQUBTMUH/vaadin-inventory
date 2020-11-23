package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.POItem;
import com.niafikra.inventory.backend.service.ItemService;
import com.niafikra.inventory.backend.service.POItemService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("items")
public class ItemsSelectorForm extends FormLayout {

    private POItemService poItemService;

    private ItemService itemService;

    Binder<POItem> binder = new BeanValidationBinder<>(POItem.class);


    public ItemsSelectorForm(POItemService poItemService, ItemService itemService) {

        this.poItemService = poItemService;
        this.itemService = itemService;

        addClassName("item-form");

        binder.bindInstanceFields(this);

        // Form Fields
        Select<Item> item = new Select<>();
        item.setLabel("Item");


        List<Item> itemList = itemService.findAll();
        item.setItemLabelGenerator(Item::getName);
        item.setItems(itemList);

        IntegerField quantity = new IntegerField("Quantity");
        Button addBtn = new Button("Add Item", add -> {
            try {
                // Create empty bean to store the item and quantity
                POItem newPOitem = new POItem();

                // Run validators and write the values to the bean
                binder.writeBean(newPOitem);

                // Call backend to store the data
                poItemService.save(newPOitem);

                // clear fields
                clearFormFields();

                // update grid
//                updateGrid();

            } catch (ValidationException e) {
                throw  new RuntimeException("Failed to add item into poItem grid " + e);
            }
        });

        binder.addStatusChangeListener(event -> addBtn.setEnabled(binder.isValid()));

        add(item, quantity, addBtn);

    }

    private void clearFormFields() {
        binder.readBean(null);
    }
}

