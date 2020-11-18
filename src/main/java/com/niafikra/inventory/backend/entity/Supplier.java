package com.niafikra.inventory.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank(message = "*required")
    @NotNull
    @NotEmpty
    private String name;

    public Supplier() {
    }

    public Supplier(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String supplierName) {
        this.name = supplierName;
    }

    // to string
    @Override
    public String toString() {
        return "Suppliers{" +
                "id=" + id +
                ", supplierName='" + name + '\'' +
                '}';
    }
}

