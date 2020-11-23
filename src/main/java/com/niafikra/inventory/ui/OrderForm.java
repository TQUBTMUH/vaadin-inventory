package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.PurchaseOrder;
import com.niafikra.inventory.backend.entity.Supplier;
import com.niafikra.inventory.backend.service.ItemService;
import com.niafikra.inventory.backend.service.POItemService;
import com.niafikra.inventory.backend.service.PurchaseOrderService;
import com.niafikra.inventory.backend.service.SupplierService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import org.vaadin.tatu.TwinColSelect;

import java.util.List;

@Route("order-form")
public class OrderForm extends FormLayout {

    private SupplierService supplierService;
    private PurchaseOrderService purchaseOrderService;
    private ItemService itemService;

    // Global components and properties
    private DatePicker orderDate = new DatePicker("Order Date");
    private Select<Supplier> supplier = new Select<>();
    private ItemsSelector itemsSelector;


    Binder<PurchaseOrder> binder = new BeanValidationBinder<>(PurchaseOrder.class);

    public OrderForm(SupplierService supplierService,
                     ItemService itemService,
                     PurchaseOrderService purchaseOrderService,
                     ItemsSelector itemsSelector) {

        this.supplierService = supplierService;
        this.itemService = itemService;
        this.purchaseOrderService = purchaseOrderService;
        this.itemsSelector = itemsSelector;

        addClassName("centered-content");

        // DatePicker

        // Supplier combobox
        supplier.setLabel("Supplier");
        List<Supplier> suppliersList = supplierService.findAll();
        supplier.setItemLabelGenerator(Supplier::getName);
        supplier.setItems(suppliersList);

        // Multiple Items Component
//        ItemsSelector multipleItems = new ItemsSelector();


        // save button configuration
        Button save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);

        save.addClickListener(click -> {
            try {
                // create empty bean to store the order
                PurchaseOrder newPurchaseOrder = new PurchaseOrder();

                // Run validators and write the values to the bean
                binder.writeBean(newPurchaseOrder);



//                newPurchaseOrder.setItems(multipleItems.getSelectedItems());
                // call backend to store
                purchaseOrderService.save(newPurchaseOrder);

                // show success notification


                // clear form fields
                clearFormFields();

            } catch (ValidationException e) {
                // create custom validation

                e.printStackTrace();
            }
        });

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        // Binder
        binder.bindInstanceFields(this);

        // Header
        Div header = new Div(new H3("Create new order"));

        // Links
        RouterLink stockList = new RouterLink("Back to stock", MainView.class);
        RouterLink newSupplier = new RouterLink("New Supplier", SupplierForm.class);

        Div link = new Div(stockList);

        add(header, newSupplier, orderDate, supplier,itemsSelector, save, link);

    }

    private void clearFormFields() {
        binder.readBean(null);
    }


}
