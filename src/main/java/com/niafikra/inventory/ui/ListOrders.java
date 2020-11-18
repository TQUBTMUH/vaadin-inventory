package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.PurchaseOrder;
import com.niafikra.inventory.backend.entity.Supplier;
import com.niafikra.inventory.backend.service.PurchaseOrderService;
import com.niafikra.inventory.backend.service.StockService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.List;

@Route("list-orders")
public class ListOrders extends VerticalLayout {

    Grid<PurchaseOrder> orders = new Grid<>(PurchaseOrder.class);

    private PurchaseOrderService purchaseOrderService;
    private StockService stockService;

    private PurchaseOrder selectedOrder;

    // Global values
    Button receive = new Button(new Icon(VaadinIcon.ANGLE_DOUBLE_DOWN));

    public ListOrders(PurchaseOrderService purchaseOrderService, StockService stockService) {
        this.purchaseOrderService = purchaseOrderService;
        this.stockService = stockService;

        // configure grid
        configureOrdersGrid();

        // Header
        Div header = new Div(new H3("ORDER LIST"));
        header.setClassName("centered-content");

        // Links
        RouterLink stockList = new RouterLink("Back to stock", MainView.class);
        Div link = new Div(stockList);

        // Select Grid and store it


        // receiveButton
        receive.addClickListener(event -> {
            selectedOrder = orders.asSingleSelect().getValue();
            stockService.customeStockUpdate(selectedOrder.getId());
            purchaseOrderService.deleteById(selectedOrder.getId());
            updateList();
        });

        add(header, orders, link);
        updateList();
    }

    private void configureOrdersGrid() {
        orders.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_NO_ROW_BORDERS);
        orders.removeColumnByKey("supplier");
        orders.removeColumnByKey("item");
        orders.setColumns("orderDate", "quantity");

        orders.addColumn(order -> {
            Supplier supplier = order.getSupplier();
            return supplier == null ? "-" : supplier.getName();
        }).setHeader("Supplier");

        orders.addColumn(orders -> {
            List<Item> item = orders.getItem();
            return item == null ? "-" : item.getClass();
        }).setHeader("Item");

        orders.addComponentColumn(this::buttons).setHeader("Action");
    }

    private Component buttons(PurchaseOrder selectedOrder) {
        Button deleteButton = new Button(new Icon(VaadinIcon.CLOSE),
                e -> createDialogAndDelete());

        return new HorizontalLayout(deleteButton, receive);
    }

    private void createDialogAndDelete() {
        Dialog alert = new Dialog();
        alert.setCloseOnEsc(false);
        alert.setCloseOnOutsideClick(false);
        Text confirm = new Text("Are you sure you want to delete this stock");

        PurchaseOrder selectedOrder = orders.asSingleSelect().getValue();

        Button confirmButton = new Button("Yes", e -> {
            deletePurchaseOrder(selectedOrder.getId());
            alert.close();
        });

        Button denyButton = new Button("No", e -> {
            alert.close();
        });

        HorizontalLayout buttons = new HorizontalLayout(confirmButton, denyButton);
        VerticalLayout layout = new VerticalLayout(confirm, buttons);

        alert.add(layout);
        add(alert);
    }

    private void deletePurchaseOrder(Long id) {
        purchaseOrderService.deleteById(id);
        updateList();
    }

    public void updateList() {
        orders.setItems(purchaseOrderService.findAll());
    }
}
