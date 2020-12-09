package com.niafikra.inventory.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainView extends AppLayout {

    public MainView() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink stockLink = new RouterLink("Stock", StockView.class);
        RouterLink newOrderLink = new RouterLink("New Order", OrderForm.class);
        RouterLink myOrderLink = new RouterLink("My orders", OrdersManagementView.class);
        RouterLink receiveLink = new RouterLink("Receive", OrdersManagementView.class);
        RouterLink newItemLink = new RouterLink("Items", ItemCRUDView.class);
        RouterLink supplierApiLink = new RouterLink("Suppliers", SupplierCRUDView.class);

        addToDrawer(new VerticalLayout(
                stockLink, myOrderLink, newOrderLink, receiveLink, newItemLink, supplierApiLink)
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
