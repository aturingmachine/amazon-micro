package com.example.amazonorders.service;

import com.example.amazonorders.model.Order;
import com.example.amazonorders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

  private OrderRepository orders;

  public OrderService(OrderRepository orders) {
    this.orders = orders;
  }

  public Order save(Order order) {
    return orders.save(order);
  }

  public List<Order> getAllOrdersForAccount(Long accountId) {
    return orders.findByAccountIdOrderByOrderDateAsc(accountId);
  }

  public Order getOne(Long id) {
    return orders.findById(id).get();
  }

  public Order updateOrder(Long id, Order order) {
    Order orderToUpdate = orders.findById(id).get();

    orderToUpdate.setOrderDate(order.getOrderDate());
    orderToUpdate.setOrderNumber(order.getOrderNumber());
    orderToUpdate.setShippingAddressId(order.getShippingAddressId());

    return orders.save(orderToUpdate);
  }

  public void deleteOrder(Long id) {
    orders.deleteById(id);
  }
}
