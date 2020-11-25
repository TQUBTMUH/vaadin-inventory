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
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "", layout = MainView.class)
public class StockView extends VerticalLayout {

    private Grid<Stock> stockGrid = new Grid<Stock>();

    private StockService stockService;

    public StockView(@Autowired StockService stockService) {
        this.stockService = stockService;

        configureStockGrid();

        // Heading
        H3 header = new H3("STOCK LIST");

        add(header, stockGrid);
        updateList();
    }

    private void configureStockGrid() {
        stockGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_NO_ROW_BORDERS);

        stockGrid.addColumn(stock -> {
            Item item = stock.getItem();
            return item == null ? "-" : item.getName();
        }).setHeader("Item Name");
        stockGrid.addColumn(Stock::getQuantity).setHeader("Quantity");

        stockGrid.addComponentColumn(stock -> {
            Button deleteBtn = new Button(new Icon(VaadinIcon.CLOSE));
            deleteBtn.addClickListener(event -> {
                Dialog alert = new Dialog();
                alert.setCloseOnEsc(false);
                alert.setCloseOnOutsideClick(false);
                Text confirm = new Text("Are you sure you want to alert this stock");
                Button confirmButton = new Button("Yes", yes -> {
                    deleteStock(stock.getId());
                    alert.close();
                });

                Button denyButton = new Button("No", no -> {
                    alert.close();
                });

                HorizontalLayout buttons = new HorizontalLayout(confirmButton, denyButton);
                VerticalLayout layout = new VerticalLayout(confirm, buttons);

                alert.add(layout);
                alert.open();
            });

            return deleteBtn;
        }).setHeader("Delete");
    }

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
