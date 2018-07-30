package com.example.amazonorders.repository;

import com.example.amazonorders.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemRepository extends JpaRepository<OrderLineItem, Long> {

  List<OrderLineItem> findByShipmentId(Long id);
}
