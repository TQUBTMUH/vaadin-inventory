package com.niafikra.inventory.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;

    @ManyToOne
    private Supplier supplier;

    @ManyToMany
    private List<Item> item;

    @Min(value = 1, message = "required more than 1")
    private int quantity;

    public PurchaseOrder(LocalDate orderDate, Supplier supplier,
                         List<Item> item,
                         @Min(value = 1, message = "required more than 1") int quantity) {
        this.orderDate = orderDate;
        this.supplier = supplier;
        this.item = item;
        this.quantity = quantity;
    }

    public PurchaseOrder() {
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

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // to string

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", supplier='" + supplier + '\'' +
                ", item='" + item + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}


