package com.example.amazonorders.service;

import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.OrderDetails;
import com.example.amazonorders.model.Shipment;
import com.example.amazonorders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

  private OrderRepository orders;

  private CrossOriginRestService rest;

  public OrderService(OrderRepository orders, CrossOriginRestService rest) {
    this.orders = orders;
    this.rest = rest;
  }

  public Order save(Order order) {
    return orders.save(order);
  }

  public List<OrderDetails> getAllOrdersForAccount(Long accountId) {

    List<Order> orderList = orders.findByAccountIdOrderByOrderDateAsc(accountId);
    List<OrderDetails> detailsList = new ArrayList<>();

    orderList.forEach(order -> detailsList.add(getOne(order.getId())));

    return detailsList;
  }

  public OrderDetails getOne(Long id) {
    Order o = orders.findById(id).get();

    OrderDetails details = new OrderDetails(o);

    //Set the address
    details.setShippingAddress(rest.getAddressFromService(o));

    HashSet<Long> list = new HashSet<>();

    o.getLineItems().forEach(item -> list.add(item.getShipmentId()));

    ArrayList<Shipment> shipments = new ArrayList<>();

    list.forEach(sId -> {
      Shipment s = rest.getShipmentFromService(sId);
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
