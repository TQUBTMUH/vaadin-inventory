package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Supplier;
import com.niafikra.inventory.backend.service.SupplierService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(layout = MainView.class)
public class SupplierForm extends VerticalLayout {

    Button save = new Button("Save");

    private SupplierService supplierService;

    TextField name = new TextField();

    Binder<Supplier> binder = new BeanValidationBinder<>(Supplier.class);

    public SupplierForm(@Autowired SupplierService supplierService) {
        this.supplierService = supplierService;

        addClassName("centered-content");

        // name field
        name.setPlaceholder("Name");

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        save.addClickListener(click -> {
            try {
                // Create empty bean to store the new Supplier into
                Supplier newSupplier = new Supplier();

                // Run validators and write the values to the bean
                binder.writeBean(newSupplier);

                // Call backend to store data
                supplierService.save(newSupplier);

                // Notification

                // clear fields
                clearFields();

            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });

        // Header
        Div header = new Div(new H3("Create New Supplier"));

        add(header, name, save);

        // Binder
        binder.bindInstanceFields(this);
    }

    private void clearFields() {
        binder.readBean(null);
    }

}
