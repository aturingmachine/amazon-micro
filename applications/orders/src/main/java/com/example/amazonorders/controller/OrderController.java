package com.example.amazonorders.controller;

import com.example.amazonorders.model.Order;
import com.example.amazonorders.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private OrderRepository orders;


  public OrderController(OrderRepository orders) {
    this.orders = orders;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Order createOrder(@RequestBody Order order) {
    return orders.save(order);
  }

  @GetMapping("/account/{accountId}")
  public List<Order> getOrdersForAccount(@PathVariable("accountId") Long id) {
    return orders.findByAccountId(id);
  }

  @GetMapping("/{id}")
  public Order getOneOrder(@PathVariable("id") Long id) {
    return orders.findById(id).get();
  }

  @PutMapping("/{id}")
  public Order updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
    Order orderToUpdate = orders.findById(id).get();

    orderToUpdate.setOrderDate(order.getOrderDate());
    orderToUpdate.setOrderNumber(order.getOrderNumber());
    orderToUpdate.setShippingAddressId(order.getShippingAddressId());

    return orders.save(orderToUpdate);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(@PathVariable("id") Long id) {
    orders.deleteById(id);
  }
}
