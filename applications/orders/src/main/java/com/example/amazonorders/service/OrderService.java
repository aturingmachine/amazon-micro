package com.example.amazonorders.service;

import com.example.amazonorders.model.Address;
import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.OrderDetails;
import com.example.amazonorders.model.Shipment;
import com.example.amazonorders.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService {

  private OrderRepository orders;

  private RestOperations rest;

  public OrderService(OrderRepository orders, RestOperations rest) {
    this.orders = orders;
    this.rest = rest;
  }

  public Order save(Order order) {
    return orders.save(order);
  }

  public List<Order> getAllOrdersForAccount(Long accountId) {
    return orders.findByAccountIdOrderByOrderDateAsc(accountId);
  }

  public OrderDetails getOne(Long id) {
    OrderDetails details = new OrderDetails();

    Order o = orders.findById(id).get();

    //Set the data we can get from the base order
    details.setOrderNumber(o.getOrderNumber());
    details.setTotalPrice(o.getTotalPrice());
    details.setLineItems(o.getLineItems());

    //Get the Address
    Address address = rest.getForObject(
        "//accounts/accounts/" + o.getAccountId() + "/addresses/" + o.getShippingAddressId(),
        Address.class);
    //Set the address
    details.setShippingAddress(address);

    HashSet<Long> list = new HashSet<>();

    o.getLineItems().forEach(item -> list.add(item.getShipmentId()));

    ArrayList<Shipment> shipments = new ArrayList<>();

    list.forEach(sId -> {
      Shipment s = rest.getForObject("//shipments/shipments/" + sId, Shipment.class);
      shipments.add(s);
    });

    details.setShipments(shipments);

    return details;
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
