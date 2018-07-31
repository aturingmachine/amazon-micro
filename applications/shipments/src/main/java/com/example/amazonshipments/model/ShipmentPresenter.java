package com.example.amazonshipments.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Setter @Getter
@NoArgsConstructor
public class ShipmentPresenter {

  private Long id;
  private Long accountId;
  private Address shippingAddress;
  private Date shippedDate;
  private Date deliveredDate;
  private List<LineItem> lineItems;

  public ShipmentPresenter(Shipment s) {
    this.id = s.getId();
    this.accountId = s.getAccountId();
    this.shippedDate = s.getShippedDate();
    this.deliveredDate = s.getDeliveredDate();
  }

}
