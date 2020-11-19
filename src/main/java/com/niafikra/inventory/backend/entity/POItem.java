package com.niafikra.inventory.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class POItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private Item item;


    private int quantity;

}
