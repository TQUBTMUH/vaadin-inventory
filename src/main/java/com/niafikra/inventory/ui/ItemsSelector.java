package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.POItem;
import com.niafikra.inventory.backend.service.POItemService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("items-selector")
public class ItemsSelector extends VerticalLayout {

    @Autowired
    private POItemService poItemService;

    Grid<POItem> poItemGrid = new Grid<>(POItem.class);
    ItemsSelectorForm itemForm = new ItemsSelectorForm();


    public ItemsSelector() {

        // Autowired
//        this.poItemService = poItemService;

        addClassName("items-selector");

        // GRID LAYOUT
        poItemGrid.addClassName("po-item-grid");
        poItemGrid.setColumns("item", "quantity");
        poItemGrid.addComponentColumn(poItem -> {
            return new Button(new Icon(VaadinIcon.CLOSE), delete -> {
                poItemService.deleteById(poItem.getId());

                // update grid
                updateGrid();
            });
        }).setHeader("Delete");
        Div header = new Div(new H3("Items to order"));


        // FORM LAYOUT
        itemForm.addClassName("item-form");


        Button saveBtn = new Button("Save");


        Div content = new Div();
        content.addClassName("content");
        content.setSizeFull();
        content.add(poItemGrid, itemForm);

        add(content, saveBtn);

        updateGrid();

    }

//    private void configureGrid() {
////        poItemGrid.setSizeFull();
//        poItemGrid.addClassName("po-item-grid");
//        poItemGrid.setColumns("item", "quantity");
//        poItemGrid.addComponentColumn(poItem -> {
//            return new Button(new Icon(VaadinIcon.CLOSE), delete -> {
//                poItemService.deleteById(poItem.getId());
//
//                // update grid
//                updateGrid();
//            });
//        }).setHeader("Delete");
//    }

    private void updateGrid() {
        poItemService.findAll();
    }

    // fetch items with quantity in POItem table
    public List<POItem> getSelectedItems() {
        return poItemService.findAll();
    }

}

