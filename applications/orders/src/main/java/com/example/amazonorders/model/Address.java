package com.example.amazonorders.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Address {

  private String street;
  private String building;
  private String city;
  private String state;
  private String zip;
  private String country;
}
