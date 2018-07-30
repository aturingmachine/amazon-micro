package com.example.amazonorders.repository;

import com.example.amazonorders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByAccountIdOrderByOrderDateAsc(Long id);
}
