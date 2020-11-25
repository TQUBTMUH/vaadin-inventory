package com.niafikra.inventory.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainView extends AppLayout {

    public MainView() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink stockLink = new RouterLink("Stock", StockView.class);
//        stockLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink newOrderLink = new RouterLink("New Order", OrderForm.class);
        RouterLink myOrderLink = new RouterLink("My orders", ListOrders.class);
        RouterLink receiveLink = new RouterLink("Receive", ListOrders.class);
        RouterLink newItemLink = new RouterLink("New Item", ItemForm.class);
        RouterLink newSupplierLink = new RouterLink("New Supplier", SupplierForm.class);

        addToDrawer(new VerticalLayout(
                stockLink, myOrderLink, newOrderLink, receiveLink, newItemLink, newSupplierLink)
        );
    }

    private void createHeader() {
        H2 logo = new H2("Inventory");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        header.setWidth("100%");
        header.getStyle().set("backgroundColor", "#e3e2e1");

        addToNavbar(header);
    }
}
