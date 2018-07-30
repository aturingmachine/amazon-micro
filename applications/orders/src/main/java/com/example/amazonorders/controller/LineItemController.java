package com.example.amazonorders.controller;

import com.example.amazonorders.model.OrderLineItem;
import com.example.amazonorders.service.LineItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders/{orderId}/lines")
public class LineItemController {

  private LineItemService itemService;

  public LineItemController(LineItemService itemService) {
    this.itemService = itemService;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderLineItem createLineItem(@PathVariable("orderId") Long id,
      @RequestBody OrderLineItem item) {

    return itemService.save(id, item);
  }

  @GetMapping("/{id}")
  public OrderLineItem getOneLineItem(@PathVariable("orderId") Long orderId,
      @PathVariable("id") Long id) {

    return itemService.getOneLineItem(orderId, id);
  }

  @GetMapping("/shipments/{id}")
  public List<OrderLineItem> getLineItemsByShipment(@PathVariable("id") Long id) {
    return itemService.findByShipment(id);
  }

  @PutMapping("/{id}")
  public OrderLineItem updateLineItem(@PathVariable("orderId") Long orderId,
      @PathVariable("id") Long id, @RequestBody OrderLineItem item) {

    return itemService.updateItem(orderId, id, item);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLineItem(@PathVariable("orderId") Long orderId, @PathVariable("id") Long id) {
    itemService.deleteItem(orderId, id);
  }
}
