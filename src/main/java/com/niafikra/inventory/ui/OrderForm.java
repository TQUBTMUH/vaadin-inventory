package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.PurchaseOrder;
import com.niafikra.inventory.backend.entity.Supplier;
import com.niafikra.inventory.backend.service.ItemService;
import com.niafikra.inventory.backend.service.POItemService;
import com.niafikra.inventory.backend.service.PurchaseOrderService;
import com.niafikra.inventory.backend.service.SupplierService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.List;

@Route(layout = MainView.class)
public class OrderForm extends VerticalLayout {

    private SupplierService supplierService;
    private PurchaseOrderService purchaseOrderService;
    private ItemService itemService;
    private ItemsSelector itemsSelector;
    private POItemService poItemService;
    private SupplierProvider provider;

    // Global components and properties
    DatePicker orderDate = new DatePicker("Order Date");
    Select<Supplier> supplier = new Select<>();


    Binder<PurchaseOrder> binder = new BeanValidationBinder<>(PurchaseOrder.class);

    public OrderForm(SupplierService supplierService, ItemService itemService,
                     PurchaseOrderService purchaseOrderService, ItemsSelector itemsSelector,
                     POItemService poItemService, SupplierProvider provider) {

        this.supplierService = supplierService;
        this.itemService = itemService;
        this.purchaseOrderService = purchaseOrderService;
        this.itemsSelector = itemsSelector;
        this.poItemService = poItemService;
        this.provider = provider;

        itemsSelector.setWidth("80%");


        // Supplier combobox
        supplier.setLabel("Supplier");
        supplier.setDataProvider(provider);

        // Items selector
        ItemsSelector itemsList = itemsSelector;

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


                // call backend to store
                itemsList.getItemsSelected().forEach(poItem -> poItemService.save(poItem));
                newPurchaseOrder.setItems(itemsSelector.getItemsSelected());
                purchaseOrderService.save(newPurchaseOrder);

                // show success notification


                // clear form fields
                clearForm();

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
        RouterLink stockList = new RouterLink("Back to stock", StockView.class);
        RouterLink newSupplier = new RouterLink("New Supplier", SupplierForm.class);

        Div link = new Div(stockList);

        add(header, newSupplier, orderDate, supplier, itemsList, save, link);

    }

    private void clearForm() {
        // clear fields
        binder.readBean(null);

        // clear grid
        itemsSelector.clearGrid();
        itemsSelector.load();
    }


}
