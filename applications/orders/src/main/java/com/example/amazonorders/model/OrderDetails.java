package com.example.amazonorders.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class OrderDetails {

  private String orderNumber;
  private Double totalPrice;
  private List<OrderLineItem> lineItems;
  private Address shippingAddress;
  private List<Shipment> shipments;

  public OrderDetails(Order o) {
    this.orderNumber = o.getOrderNumber();
    this.totalPrice = o.getTotalPrice();
    this.lineItems = o.getLineItems();
  }
}
