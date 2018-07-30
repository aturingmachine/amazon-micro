package com.example.amazonshipments.repository;

import com.example.amazonshipments.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

  List<Shipment> findByAccountId(Long id);
}
