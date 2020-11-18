package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.Stock;
import com.niafikra.inventory.backend.service.StockService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
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
import org.springframework.beans.factory.annotation.Autowired;


@Route("stock")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    Grid<Stock> stockGrid = new Grid<>(Stock.class);

    private StockService stockService;

    public MainView(@Autowired StockService stockService) {
        this.stockService = stockService;

        setSizeFull();
        configureStockGrid();

        // Heading
        Div header = new Div(new H3("STOCK LIST"));
        header.setClassName("centered-content");


        // Links
        RouterLink orderList = new RouterLink("My orders", ListOrders.class);
        RouterLink newOrder = new RouterLink("New Order", OrderForm.class);
        RouterLink receive = new RouterLink("Receive", ListOrders.class);
        RouterLink newItem = new RouterLink("New Item", ItemForm.class);

        HorizontalLayout links = new HorizontalLayout(newOrder, orderList, receive, newItem);
        Div linkBlock = new Div(links);

        add(linkBlock, header, stockGrid);
        updateList();
    }

//    private void createLinks() {
    // For newOrder
//        String newOrderLink = UI.getCurrent().getRouter().getUrl(OrderForm.class);
//        newOrder = new Anchor(newOrderLink, "New Order");
//    }

    private void configureStockGrid() {
        stockGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_NO_ROW_BORDERS);
        stockGrid.setSizeFull();
        stockGrid.removeColumnByKey("item");
        stockGrid.setColumns("quantity");
        stockGrid.addColumn(stock -> {
            Item item = stock.getItem();
            return item == null ? "-" : item.getName();
        }).setHeader("Item Name");


        stockGrid.addComponentColumn(stock -> {
            Button button = new Button(new Icon(VaadinIcon.CLOSE));
            button.addClickListener(event -> {
                Dialog alert = new Dialog();
                alert.setCloseOnEsc(false);
                alert.setCloseOnOutsideClick(false);
                Text confirm = new Text("Are you sure you want to alert this stock");
                Button confirmButton = new Button("Yes", yes -> {
                    deleteStock(stock.getId());
                    alert.close();
                });

                alert.add(confirm,confirmButton);
                alert.open();
            });
            return button;
        }).setHeader("Delete");
    }

//    private Button deleteButton(Stock stock) {
//        delete = new Button(new Icon(VaadinIcon.CLOSE));
//
//        delete.addClickListener(e -> {
//            Dialog alert = new Dialog();
//            alert.setCloseOnEsc(false);
//            alert.setCloseOnOutsideClick(false);
//            Text confirm = new Text("Are you sure you want to alert this stock");
//            Button confirmButton = new Button("Yes", yes -> {
//                Stock selectedStock = stockGrid.asSingleSelect().getValue();
//                deleteStock(selectedStock.getId());
//                alert.close();
//            });
//
//            Button denyButton = new Button("No", no -> {
//                alert.close();
//            });
//
//            HorizontalLayout buttons = new HorizontalLayout(confirmButton, denyButton);
//            VerticalLayout layout = new VerticalLayout(confirm, buttons);
//
//            alert.add(layout);
//            add(alert);
//        });
//
//        return delete;
//    }

//    private Button deleteButton(Stock stock) {
//        delete.addClickListener(e -> {
//            stockService.deleteById(stockGrid.asSingleSelect().getValue().getId());
//        });
//
//        return delete;
//    }

//    private void createDialogAndDelete(Long id) {
//        Dialog alert = new Dialog();
//        alert.setCloseOnEsc(false);
//        alert.setCloseOnOutsideClick(false);
//        Text confirm = new Text("Are you sure you want to alert this stock");
//        Button confirmButton = new Button("Yes", e -> {
//            deleteStock(id);
//            alert.close();
//        });
//
//        Button denyButton = new Button("No", e -> {
//            alert.close();
//        });
//
//        HorizontalLayout buttons = new HorizontalLayout(confirmButton, denyButton);
//        VerticalLayout layout = new VerticalLayout(confirm, buttons);
//
//        alert.add(layout);
//        add(alert);
//    }

    // update stock list
    private void updateList() {
        stockGrid.setItems(stockService.findAll());
    }

    // Delete stock item
    private void deleteStock(Long theId) {
        stockService.deleteById(theId);
        updateList();
    }

}
