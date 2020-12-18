package com.niafikra.inventory.backend.entity;

import javax.persistence.*;

@Entity
@Table
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Item item;

    private int quantity;

    public Stock() {
    }

    public Stock(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

