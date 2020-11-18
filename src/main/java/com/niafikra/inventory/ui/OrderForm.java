package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.PurchaseOrder;
import com.niafikra.inventory.backend.entity.Supplier;
import com.niafikra.inventory.backend.service.ItemService;
import com.niafikra.inventory.backend.service.PurchaseOrderService;
import com.niafikra.inventory.backend.service.SupplierService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Route("order-form")
public class OrderForm extends FormLayout {

    private SupplierService supplierService;
    private final PurchaseOrderService purchaseOrderService;
    private ItemService itemService;

    // Global components and properties
    DatePicker orderDate = new DatePicker("Order Date");
    ComboBox<Supplier> suppliers = new ComboBox<>();
    ComboBox<Item> items = new ComboBox<>();
    IntegerField quantity = new IntegerField("Quantity");


    Button save = new Button("Save");
    Binder<PurchaseOrder> binder = new Binder<>(PurchaseOrder.class);

    public OrderForm(SupplierService supplierService, ItemService itemService,
                     PurchaseOrderService purchaseOrderService) {

        this.supplierService = supplierService;
        this.itemService = itemService;
        this.purchaseOrderService = purchaseOrderService;

        addClassName("centered-content");

        // DatePicker


        // Supplier combobox
        suppliers.setLabel("Supplier");
        List<Supplier> suppliersList = supplierService.findAll();
        suppliers.setItemLabelGenerator(Supplier::getName);
        suppliers.setItems(suppliersList);

        // Items
        items.setLabel("Item Name");
        List<Item> itemList = itemService.findAll();
        items.setItemLabelGenerator(Item::getName);
        items.setItems(itemList);

        // Quantity

        // Binder
        binder.bindInstanceFields(this);

        // save button configuration
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);

        save.addClickListener(click -> {
            try {
                // create empty bean to store the order
                PurchaseOrder newPurchaseOrder = new PurchaseOrder();

                // Run validators and write the values to the bean
                binder.writeBean(newPurchaseOrder);

                // call backend to store
                purchaseOrderService.save(newPurchaseOrder);

                // show success notification


                // clear form fields
                clearFormFields();

            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        // Header
        Div header = new Div(new H3("Create new order"));

        // Links
        RouterLink stockList = new RouterLink("Back to stock", MainView.class);
        Div link = new Div(stockList);

        add(header, orderDate, suppliers, items, quantity, save, link);
    }

    private void clearFormFields() {
        binder.readBean(null);
    }

//    private Component createButtonLayout() {
//        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        save.addClickShortcut(Key.ENTER);
//        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));
//
//        // Save Event
//        save.addClickListener(click -> {
//            try {
//                // create empty bean to store the order
//                PurchaseOrder newPurchaseOrder = new PurchaseOrder();
//
//                // Run validators and write the values to the bean
//                binder.writeBean(newPurchaseOrder);
//
//                // call backend to store
//                purchaseOrderService.save(newPurchaseOrder);
//
//                // show success notification
//
//
//                // clear form fields
//
//            } catch (ValidationException e) {
//                e.printStackTrace();
//            }
//        });
//
//        return save;
//    }


}
