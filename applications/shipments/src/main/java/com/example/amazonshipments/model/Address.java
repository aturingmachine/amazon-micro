package com.example.amazonshipments.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

  private Long id;
  private String street;
  private String building;
  private String city;
  private String state;
  private String zip;
  private String country;
}

