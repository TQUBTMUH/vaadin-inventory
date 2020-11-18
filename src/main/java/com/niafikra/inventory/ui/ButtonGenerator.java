package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Stock;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.ValueProvider;

public class ButtonGenerator implements ValueProvider<Stock, Component> {
    @Override
    public Component apply(Stock stock) {
        return new Button(new Icon(VaadinIcon.CLOSE));
    }
}
