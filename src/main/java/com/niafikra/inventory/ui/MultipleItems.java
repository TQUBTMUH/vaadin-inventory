package com.niafikra.inventory.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the multiple-items template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("multiple-items")
@JsModule("./multiple-items.js")
public class MultipleItems extends PolymerTemplate<MultipleItems.MultipleItemsModel> {

    /**
     * Creates a new MultipleItems.
     */
    public MultipleItems() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between MultipleItems and multiple-items
     */
    public interface MultipleItemsModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
