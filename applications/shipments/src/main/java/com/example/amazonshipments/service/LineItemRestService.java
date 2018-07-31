package com.example.amazonshipments.service;

import com.example.amazonshipments.model.LineItem;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class LineItemRestService {

  private RestOperations rest;

  public LineItemRestService(RestOperations rest) {
    this.rest = rest;
  }

  @HystrixCommand(fallbackMethod = "fallBackGetLineItemsForShipment")
  public LineItem[] getLineItemsForShipment(Long shipmentId) {
    return rest.getForObject("//orders/orders/0/lines/shipments/" + shipmentId, LineItem[].class);
  }

  //Return a stubbed list with a LineItem with quantity 1, that way the consumer can know
  //that the remote call failed and parse the info properly.
  public LineItem[] fallBackGetLineItemsForShipment(Long shipmentId, Throwable t) {
    t.printStackTrace();
    LineItem[] list = new LineItem[1];
    LineItem i = new LineItem();
    i.setQuantity(-1);
    list[0] = i;
    return list;
  }
}
