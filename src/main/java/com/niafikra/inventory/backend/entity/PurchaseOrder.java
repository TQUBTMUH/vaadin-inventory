package com.niafikra.inventory.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
    private LocalDate orderDate;

    @ManyToOne
    @NotNull
//    @NotBlank
    private Supplier supplier;

    @ManyToOne
    @NotNull
//    @NotBlank
    private Item item;

//    @Min(value = 1, message = "required more than 1")
    @NotNull
//    @NotBlank
    private int quantity;


    public PurchaseOrder() {
    }

    public PurchaseOrder(LocalDate orderDate, Supplier supplier, Item item, int quantity) {
        this.orderDate = orderDate;
        this.supplier = supplier;
        this.item = item;
        this.quantity = quantity;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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


