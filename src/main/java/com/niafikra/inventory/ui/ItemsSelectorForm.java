package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.POItem;
import com.niafikra.inventory.backend.service.ItemService;
import com.niafikra.inventory.backend.service.POItemService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
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

    // Fields
    Select<Item> item = new Select<>();
    IntegerField quantity = new IntegerField("Quantity");
    Button addBtn = new Button("Add Item");


    Binder<POItem> binder = new BeanValidationBinder<>(POItem.class);

//    public ItemsSelectorForm() {
//    }

    public ItemsSelectorForm(@Autowired POItemService poItemService, @Autowired ItemService itemService) {
        this.poItemService = poItemService;
        this.itemService = itemService;

        addClassName("item-form");
        setMaxWidth("500px");
        setSizeFull();

        binder.bindInstanceFields(this);


        // Form Fields
        item.setLabel("Item");


        List<Item> itemList = itemService.findAll();
        item.setItemLabelGenerator(Item::getName);
        item.setItems(itemList);

        addBtn.addClickListener (add -> {
            try {
                // Create empty bean to store the item and quantity
                POItem newPOitem = new POItem();

                // Run validators and write the values to the bean
                binder.writeBean(newPOitem);

                // Call backend to store the data
                poItemService.save(newPOitem);

                // clear fields
                clearFormFields();

                // update grid or cause a redirect into grid


            } catch (ValidationException e) {
                throw  new RuntimeException("Failed to add item into poItem grid " + e);
            }
        });

        binder.addStatusChangeListener(event -> addBtn.setEnabled(binder.isValid()));

        Div header = new Div(new H3("Form"));

        add(header, item, quantity, addBtn);
    }

    private void clearFormFields() {
        binder.readBean(null);
    }
}

