package com.niafikra.inventory.backend.dao;

import com.niafikra.inventory.backend.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    public Item findByName(String itemName);
    public Item findByCode(String code);
    public Long countAllByNameStartingWith(String itemFilter);

    Page<Item> findAllByName(String name, Pageable pageable);

    Page<Item> findAllByCode(String code, Pageable pageable);

    Page<Item> findAllByCodeAndName(String code, String name, Pageable pageable);

    Long countAllByCodeAndName(String code, String name);

    Long countAllByCode(String code);

    Long countAllByName(String name);
}
