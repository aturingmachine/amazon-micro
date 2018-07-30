package com.example.amazonorders.controller;

import com.example.amazonorders.model.OrderLineItem;
import com.example.amazonorders.repository.LineItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders/{orderId}/lines")
public class LineItemController {

  private LineItemRepository lineItems;

  public LineItemController(LineItemRepository lineItems) {
    this.lineItems = lineItems;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderLineItem createLineItem(@RequestBody OrderLineItem item) {
    return lineItems.save(item);
  }

  @GetMapping("/{id}")
  public OrderLineItem getOneLineItem(@PathVariable("id") Long id) {
    return lineItems.findById(id).get();
  }

  @GetMapping("/shipments/{id}")
  public List<OrderLineItem> getLineItemsByShipment(@PathVariable("id") Long id) {
    return lineItems.findByShipmentId(id);
  }

  @PutMapping("/{id}")
  public OrderLineItem updateLineITem(@PathVariable("id") Long id, @RequestBody OrderLineItem item) {
    OrderLineItem itemToUpdate = lineItems.findById(id).get();

    itemToUpdate.setProductId(item.getProductId());
    itemToUpdate.setQuantity(item.getQuantity());
    itemToUpdate.setShipmentId(item.getShipmentId());

    return lineItems.save(itemToUpdate);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLineItem(@PathVariable("id") Long id) {
    lineItems.deleteById(id);
  }
}
