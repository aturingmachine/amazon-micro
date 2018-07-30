package com.example.accounts.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String street;
  private String building;
  private String city;
  private String state;
  private String zip;
  private String country;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;


  public Long getAccount() {
    return this.account.getId();
  }
}
