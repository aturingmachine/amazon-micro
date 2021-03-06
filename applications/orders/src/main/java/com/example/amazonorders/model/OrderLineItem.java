package com.example.amazonorders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class OrderLineItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
  private Integer quantity;
  private Double price;
  private Double totalPrice;
  private Long shipmentId;

  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonIgnoreProperties("lineItems")
  private Order order;

  public Double getTotalPrice() {
    if (this.price != null && this.quantity != null) {
      return this.price * this.quantity;
    } else {
      return 0.0;
    }
  }

  public Long getOrder() {
    return this.order.getId();
  }
}
