package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.POItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Component
public class ItemsSelector extends HorizontalLayout implements POItemForm.OnSaveHandler {

    Grid<POItem> itemsGrid = new Grid<POItem>();

    private POItemForm itemForm;

    private List<POItem> items = new LinkedList<>();

    public ItemsSelector(POItemForm itemForm) {
        this.itemForm = itemForm;
    }

    @PostConstruct
    private void build(){
        H3 header = new H3("Items to order");

        itemsGrid.addColumn(poItem -> {
            Item gridItem = poItem.getItem();
            return gridItem.getName();
        }).setHeader("Item");
        itemsGrid.addColumn(poItem -> poItem.getQuantity()).setHeader("Quantity");
        itemsGrid.addComponentColumn(poItem -> {
            return new Button(new Icon(VaadinIcon.CLOSE), event -> {

            });
        }).setHeader("Delete");

        itemsGrid.setMaxWidth("600px");

        add(
                new VerticalLayout(header, itemsGrid),
                new VerticalLayout(itemForm)
        );

        itemForm.setOnSaveHandler(this);

        load();
    }


    public void load(){
        itemsGrid.setItems(items);
    }
    @Override
    public void onSave(POItem item) {
        //add item to the grid;
        items.add(item);
        load();
    }

    public List<POItem> getSelectedItems(){
        return new LinkedList<>(items);
    }
}


