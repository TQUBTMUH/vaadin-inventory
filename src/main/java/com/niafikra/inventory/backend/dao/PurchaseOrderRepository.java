package com.niafikra.inventory.backend.dao;

import com.niafikra.inventory.backend.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
//    @Query(value = "SELECT item.id FROM item INNER JOIN purchase_order_item ON " +
//            "item.id = purchase_order_item.item_id WHERE purchase_order_item.purchase_order_id = :poId",
//            nativeQuery = true)
//    Long findByItemId(@Param("poId") Long purchaseOrderId);


}
