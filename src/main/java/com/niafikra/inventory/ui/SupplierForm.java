package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Supplier;
import com.niafikra.inventory.backend.service.SupplierService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

import java.util.Collection;

@Route(layout = MainView.class)
public class SupplierForm extends VerticalLayout {

    private SupplierService supplierService;

    GridCrud<Supplier> supplierGridCrud = new GridCrud<>(Supplier.class);

    public SupplierForm(@Autowired SupplierService supplierService) {
        this.supplierService = supplierService;


        // configuring supplier grid
        supplierGridCrud.getCrudFormFactory().setUseBeanValidation(true);
        supplierGridCrud.getGrid().removeColumnByKey("id");

        // customizing fields
        supplierGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "name");
        supplierGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "name");
        supplierGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "name");
        supplierGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "name");

        // CRUD Listener
        supplierGridCrud.setCrudListener(new CrudListener<Supplier>() {
            @Override
            public Collection<Supplier> findAll() {

                return supplierService.findAll();
            }

            @Override
            public Supplier add(Supplier newSupplier) {
                return supplierService.save(newSupplier);
            }

            @Override
            public Supplier update(Supplier supplier) {
                return supplierService.update(supplier);
            }

            @Override
            public void delete(Supplier supplier) {
                supplierService.delete(supplier);
            }
        });


        // add component
        add(supplierGridCrud);
    }
}
