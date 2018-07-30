package com.example.amazonshipments.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor
public class Shipment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long accountId;
  private Long shippingAddressId;
  private Date shippedDate;
  private Date deliveredDate;
  @JsonInclude
  @Transient
  private List<LineItem> lineItems;

}
