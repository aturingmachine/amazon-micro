package com.example.amazonorders.service;

import com.example.amazonorders.model.OrderLineItem;
import com.example.amazonorders.repository.LineItemRepository;
import com.example.amazonorders.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LineItemService {

  private OrderRepository orders;

  private LineItemRepository items;

  private RestOperations rest;

  public LineItemService(OrderRepository orders, LineItemRepository items, RestOperations rest) {
    this.orders = orders;
    this.items = items;
    this.rest = rest;
  }

  public OrderLineItem save(Long orderId, OrderLineItem item) {

    checkIfOrderExists(orderId);
    checkIfCrossResourceExists("shipments", item.getShipmentId());
    checkIfCrossResourceExists("products", item.getProductId());

    Double price = rest.getForObject("//products/products/" + item.getProductId() + "/price",
        Double.class);

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

  private void checkIfCrossResourceExists(String resourceType, Long id) {
    String url = "//" + resourceType + "/" + resourceType + "/" + id;
    if (rest.getForEntity(url, String.class).getStatusCode().value() != HttpStatus.OK.value()) {
      throw new NoSuchElementException(
          "Resource: " + resourceType + " of ID: " + id + " could not be found");
    }
  }
}
