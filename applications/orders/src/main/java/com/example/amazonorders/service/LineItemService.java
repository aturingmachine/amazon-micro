package com.example.amazonorders.service;

import com.example.amazonorders.model.OrderLineItem;
import com.example.amazonorders.repository.LineItemRepository;
import com.example.amazonorders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LineItemService {

  private OrderRepository orders;

  private LineItemRepository items;

  private CrossOriginRestService restService;

  public LineItemService(OrderRepository orders, LineItemRepository items, CrossOriginRestService restService) {
    this.orders = orders;
    this.items = items;
    this.restService = restService;
  }

  public OrderLineItem save(Long orderId, OrderLineItem item) {

    checkIfOrderExists(orderId);
    restService.checkIfCrossResourceExists("shipments", item.getShipmentId());
    restService.checkIfCrossResourceExists("products", item.getProductId());

    Double price = restService.getProductPrice(item.getProductId());

    item.setOrder(orders.findById(orderId).get());
    item.setPrice(price);

    return items.save(item);
  }

  public OrderLineItem getOneLineItem(Long orderId, Long id) {

    checkIfOrderExists(orderId);

    return items.findById(id).get();

  }

  public List<OrderLineItem> findByShipment(Long id) {
    return items.findByShipmentId(id);
  }

  public OrderLineItem updateItem(Long orderId, Long id, OrderLineItem newItem) {

    checkIfOrderExists(orderId);

    OrderLineItem oldItem = items.findById(id).get();
    oldItem.setQuantity(newItem.getQuantity());
    oldItem.setShipmentId(newItem.getShipmentId());
    oldItem.setPrice(restService.getProductPrice(oldItem.getProductId()));

    return items.save(oldItem);
  }

  public void deleteItem(Long orderId, Long id) {

    checkIfOrderExists(orderId);

    items.deleteById(id);
  }

  private void checkIfOrderExists(Long orderId) {
    if (!orders.findById(orderId).isPresent()) {
      throw new NoSuchElementException();
    }
  }
}
