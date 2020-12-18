package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.Stock;
import com.niafikra.inventory.backend.service.StockService;
import com.niafikra.inventory.backend.service.StockServiceImp;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

import static com.niafikra.inventory.backend.service.StockServiceImp.*;


@Route(value = "", layout = MainView.class)
public class StockView extends VerticalLayout {

    private StockService stockService;
    private StocksDataProvider stocksDataProvider;

    private Grid<Stock> stockGrid = new Grid<Stock>();
    private TextField itemNameFilter;
    private IntegerField quantityFilter;

    private StockFilter filter;
    private ConfigurableFilterDataProvider<Stock, Void, StockFilter> filterConfigurableProvider;

    public StockView(StockService stockService, StocksDataProvider stocksDataProvider) {
        this.stockService = stockService;
        this.stocksDataProvider = stocksDataProvider;

        stockGrid.setDataProvider(stocksDataProvider);

        filter = new StockFilter();
        filterConfigurableProvider = stocksDataProvider.withConfigurableFilter();
        filterConfigurableProvider.setFilter(filter);

        // Heading
        H3 header = new H3("STOCK LIST");

        add(header, stockGrid);
        updateList();
    }

    @PostConstruct
    private void configureStockGrid() {
        stockGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_NO_ROW_BORDERS);

        stockGrid.addColumn(stock -> {
            Item item = stock.getItem();
            return item == null ? "-" : item.getName();
        }).setHeader("Item Name").setKey("name");
        stockGrid.addColumn(Stock::getQuantity).setHeader("Quantity").setKey("quantity");

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


        // filter row
        HeaderRow filterRow = stockGrid.appendHeaderRow();

        // itemName filter
        itemNameFilter = new TextField();
        itemNameFilter.addValueChangeListener(event -> {
            filter.setItem(stockService.findByItemName(event.getValue()));
            filterConfigurableProvider.refreshAll();
        });
        itemNameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        filterRow.getCell(stockGrid.getColumnByKey("name"))
                .setComponent(itemNameFilter);
        itemNameFilter.setSizeFull();
        itemNameFilter.setPlaceholder("Filter");

        // quantity filter
        quantityFilter = new IntegerField();
        quantityFilter.addValueChangeListener(event -> {
            filter.setQuantity(event.getValue());
            filterConfigurableProvider.refreshAll();
        });
        quantityFilter.setValueChangeMode(ValueChangeMode.LAZY);
        filterRow.getCell(stockGrid.getColumnByKey("quantity"))
                .setComponent(quantityFilter);
        quantityFilter.setSizeFull();
        quantityFilter.setPlaceholder("Filter");
    }

    // update stock list
    private void updateList() {
        filterConfigurableProvider.refreshAll();
    }

    // refresh stock list, used by filters
//    private void refresh() {
//        if (itemNameFilter.isEmpty() && quantityFilter.isEmpty()) {
//            updateList();
//        } else if (!(itemNameFilter.isEmpty()) && quantityFilter.isEmpty()) {
//            stockService.findAll(filter);
//        } else if (itemNameFilter.isEmpty() && !(quantityFilter.isEmpty())) {
//            stockService.findAll(filter);
//            // all filter fields have value
//        } else {
//            stockService.findAll(filter);
//        }
//    }

    // Delete stock item
    private void deleteStock(Long theId) {
        stockService.deleteById(theId);
        updateList();
    }

}
