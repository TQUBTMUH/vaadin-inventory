package com.niafikra.inventory.backend.dao;

import com.niafikra.inventory.backend.entity.POItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface POItemRepository extends JpaRepository<POItem, Long> {

}
