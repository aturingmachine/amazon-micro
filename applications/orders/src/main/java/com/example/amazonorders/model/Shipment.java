package com.example.amazonorders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class Shipment {

  private Long id;
  private Date shippedDate;
  private Date deliveredDate;

  @JsonIgnoreProperties(value = {"order"})
  private List<OrderLineItem> lineItems;
}
