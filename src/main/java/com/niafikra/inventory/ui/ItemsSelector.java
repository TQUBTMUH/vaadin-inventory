package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Item;
import com.niafikra.inventory.backend.entity.POItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ItemsSelector extends HorizontalLayout implements POItemForm.OnSaveHandler {

    private final POItemForm poItemForm;

    protected Grid<POItem> poItemGrid = new Grid<POItem>();

    private final List<POItem> poItems = new LinkedList<>();

    public ItemsSelector(@Autowired POItemForm poItemForm) {

        // Autowired
        this.poItemForm = poItemForm;

        addClassName("items-selector");

        // GRID LAYOUT
        poItemGrid.addColumn(poItem -> {
            Item gridItem = poItem.getItem();
            return gridItem.getName();
        }).setHeader("Item");

        poItemGrid.addColumn(POItem::getQuantity).setHeader("Quantity");

        poItemGrid.addComponentColumn(poItem -> {
            return new Button(new Icon(VaadinIcon.CLOSE), delete -> {

                // Create pop up dialog here
                POItem itemToDelete = poItem;
                poItems.remove(itemToDelete);

                // update grid
                load();

            });
        }).setHeader("Delete");
        H3 header = new H3("Items to order");
        add(new VerticalLayout(header, poItemGrid), poItemForm);

        // update grid
        poItemForm.setOnSaveHandler(this);
        load();
    }

    public void load() {
        poItemGrid.setItems(poItems);
    }

    @Override
    public void onSave(POItem item) {
        // Add items to the grid
        if (poItems.contains(item)) {
            Notification exist = new Notification(item.getItem().getName()
                    + "already exist in item list", 2000, Notification.Position.MIDDLE);
            exist.open();
        } else {
            poItems.add(item);
            load();
        }
//        poItems.add(item);
//        load();
    }

    protected List<POItem> getItemsSelected() {
        return poItems;
    }

    public void clearGrid() {
        poItems.clear();
    }
}


