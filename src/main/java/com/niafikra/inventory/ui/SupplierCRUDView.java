package com.niafikra.inventory.ui;

import com.niafikra.inventory.backend.entity.Supplier;
import com.niafikra.inventory.backend.service.SupplierService;
import com.niafikra.inventory.backend.service.SupplierServiceImp.SupplierFilter;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;


import javax.annotation.PostConstruct;

@Route(layout = MainView.class)
public class SupplierCRUDView extends VerticalLayout {

    private SupplierService supplierService;
    private SuppliersProvider suppliersProvider;
    private ConfigurableFilterDataProvider<Supplier, Void, SupplierFilter> filterDataProvider;

    GridCrud<Supplier> supplierGridCrud = new GridCrud<>(Supplier.class);
    TextField nameFilterField;
    SupplierFilter filter;

    public SupplierCRUDView(SupplierService supplierService, SuppliersProvider suppliersProvider) {
        this.supplierService = supplierService;
        this.suppliersProvider = suppliersProvider;

        filter = new SupplierFilter();
        filterDataProvider = suppliersProvider.withConfigurableFilter();
        filterDataProvider.setFilter(filter);
    }

    @PostConstruct
    private void build() {
        // configuring supplier grid
        supplierGridCrud.getCrudFormFactory().setUseBeanValidation(true);
        supplierGridCrud.getGrid().removeColumnByKey("id");
        supplierGridCrud.getGrid().setDataProvider(suppliersProvider);

        // filter configuration
        HeaderRow filterRow = supplierGridCrud.getGrid().appendHeaderRow();

        // name filter
        nameFilterField = new TextField();
        nameFilterField.addValueChangeListener(event -> {
            filter.setName(event.getValue());
            filterDataProvider.refreshAll();
        });
        nameFilterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterRow.getCell(supplierGridCrud.getGrid().getColumnByKey("name"))
                .setComponent(nameFilterField);
        nameFilterField.setSizeFull();
        nameFilterField.setPlaceholder("Filter");

        // customizing fields
        supplierGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "name");
        supplierGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "name");
        supplierGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "name");
        supplierGridCrud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "name");


//        supplierGridCrud.setFindAllOperation(() -> supplierService.findAll());

        supplierGridCrud.setFindAllOperation(filterDataProvider);
        supplierGridCrud.setAddOperation(item -> supplierService.save(item));
        supplierGridCrud.setUpdateOperation(item -> supplierService.update(item));
        supplierGridCrud.setDeleteOperation(item -> supplierService.delete(item));

        // add component
        add(supplierGridCrud);
    }
}
