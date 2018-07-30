package com.example.amazonorders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
  private List<OrderLineItem> lineItems;
  private String orderNumber;
  private Date orderDate;

  private Double totalPrice;

  public void setTotalPrice(Double price) {}

  @JsonIgnore
  public Double getTotalPrice() {
    if (this.lineItems != null) {
      this.totalPrice = 0.0;
      this.lineItems.forEach(item -> this.totalPrice += item.getTotalPrice());
    }

    return this.totalPrice;
  }
}
