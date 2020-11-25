package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.POItem;
import com.niafikra.inventory.backend.service.ItemService;
import com.niafikra.inventory.backend.service.POItemService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope("prototype")
public class POItemForm extends VerticalLayout {

    private ItemService itemService;

    // Fields
    Select<Item> item = new Select<>();
    IntegerField quantity = new IntegerField("Quantity");
    Button addBtn = new Button("Add Item");

    Binder<POItem> binder = new BeanValidationBinder<>(POItem.class);

    private OnSaveHandler onSaveHandler;

    public POItemForm(@Autowired ItemService itemService) {

        this.itemService = itemService;
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
                onSaveHandler.onSave(newPOitem);

                // clear fields
                clearFormFields();

                // update grid or cause a redirect into grid


            } catch (ValidationException e) {
                throw  new RuntimeException("Failed to add item into poItem grid " + e);
            }
        });

        binder.addStatusChangeListener(event -> addBtn.setEnabled(binder.isValid()));

        H3 header = new H3("Form");

        add(header, item, quantity, addBtn);
    }

    private void clearFormFields() {
        binder.readBean(null);
    }

    public void setOnSaveHandler(OnSaveHandler onSaveHandler) {
        this.onSaveHandler = onSaveHandler;
    }

    public interface OnSaveHandler{

        void onSave(POItem poItem);
    }
}

