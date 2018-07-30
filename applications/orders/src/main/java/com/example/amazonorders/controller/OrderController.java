package com.example.amazonorders.controller;

import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.OrderDetails;
import com.example.amazonorders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Order createOrder(@RequestBody Order order) {
    return orderService.save(order);
  }

  @GetMapping("/account/{accountId}")
  public List<Order> getOrdersForAccount(@PathVariable("accountId") Long id) {
    return orderService.getAllOrdersForAccount(id);
  }

  @GetMapping("/{id}")
  public OrderDetails getOneOrder(@PathVariable("id") Long id) {
    return orderService.getOne(id);
  }

  @PutMapping("/{id}")
  public Order updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
    return orderService.updateOrder(id, order);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(@PathVariable("id") Long id) {
    orderService.deleteOrder(id);
  }
}
