package com.example.amazonorders.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long accountId;
  private Long shippingAddressId;

  @OneToMany
  @JoinColumn(name = "order_id")
  private List<OrderLineItem> lineItems;
  private String orderNumber;
  private Date orderDate;
  private Double totalPrice;


  public Double getTotalPrice() {
    if (this.lineItems != null) {
      this.totalPrice = 0.0;
      this.lineItems.forEach(item -> this.totalPrice += item.getTotalPrice());
    }

    return this.totalPrice;
  }

  public void setTotalPrice() {
    this.totalPrice = this.getTotalPrice();
  }
}
