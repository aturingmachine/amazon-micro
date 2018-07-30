package com.example.amazonshipments.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LineItem {

  private Long productId;
  private Integer quantity;
  private Double price;
  private Double totalPrice;
  private Long shipmentId;
}
