package com.niafikra.inventory.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;

    @ManyToOne
    @NotNull
    private Supplier supplier;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER)
    private List<POItem> items;

    public PurchaseOrder() {
    }

    public PurchaseOrder(LocalDate orderDate, Supplier supplier) {
        this.orderDate = orderDate;
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<POItem> getItems() {
        return items;
    }

    public void setItems(List<POItem> items) {
        this.items = items;
    }

    // to string
    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", supplier='" + supplier + '\'' +

                '}';
    }
}


