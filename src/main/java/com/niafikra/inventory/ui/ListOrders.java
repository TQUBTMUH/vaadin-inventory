package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.POItem;
import com.niafikra.inventory.backend.entity.PurchaseOrder;
import com.niafikra.inventory.backend.entity.Supplier;
import com.niafikra.inventory.backend.service.PurchaseOrderService;
import com.niafikra.inventory.backend.service.StockService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.List;
import java.util.ListIterator;

@Route("list-orders")
public class ListOrders extends VerticalLayout {

    Grid<PurchaseOrder> orders = new Grid<>(PurchaseOrder.class);

    private PurchaseOrderService purchaseOrderService;
    private StockService stockService;


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

        add(header, orders, link);
        updateList();
    }

    private void configureOrdersGrid() {
        orders.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_NO_ROW_BORDERS);
        orders.removeColumnByKey("supplier");
        orders.removeColumnByKey("items");
        orders.setColumns("orderDate");

        // order column
        orders.addColumn(order -> {
            Supplier supplier = order.getSupplier();
            return supplier == null ? "-" : supplier.getName();
        }).setHeader("Supplier");

        // item column
        orders.addColumn(order -> {
            List<POItem> orderList = order.getItems();
            return orderList;
        }).setHeader("Item");

        // Receive button configuration
        orders.addComponentColumn(purchaseOrder -> {
            Button receiveBtn = new Button(new Icon(VaadinIcon.ANGLE_DOUBLE_DOWN));
            receiveBtn.addClickListener(event -> {
                // update stock and delete item in purchaseOrder
                Long orderId = purchaseOrder.getId();
                stockService.customeStockUpdate(orderId);
                purchaseOrderService.deleteById(purchaseOrder.getId());

                // notification


                updateList();
            });

            return receiveBtn;
        }).setHeader("Receive");


        // Delete button configuration
        orders.addComponentColumn(purchaseOrder -> {
            Button deleteBtn = new Button(new Icon(VaadinIcon.CLOSE));
            deleteBtn.addClickListener(event -> {
                Dialog confirm = new Dialog();
                confirm.setCloseOnEsc(false);
                confirm.setCloseOnOutsideClick(false);

                Text message = new Text("Are you sure you want to delete this order?");

                Button confirmBtn = new Button("Yes", Yes -> {
                    purchaseOrderService.deleteById(purchaseOrder.getId());
                    confirm.close();
                    updateList();
                });

                Button denyBtn = new Button("No", No -> {
                    confirm.close();
                });

                HorizontalLayout btnLayout = new HorizontalLayout(confirmBtn, denyBtn);
                VerticalLayout dialogLayout = new VerticalLayout(message, btnLayout);

                confirm.add(dialogLayout);
                confirm.open();
            });

            return deleteBtn;
        }).setHeader("Delete");

    }

    public void updateList() {
        orders.setItems(purchaseOrderService.findAll());
    }
}
