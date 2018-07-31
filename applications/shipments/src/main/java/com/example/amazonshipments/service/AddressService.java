package com.example.amazonshipments.service;

import com.example.amazonshipments.model.Address;
import com.example.amazonshipments.model.Shipment;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class AddressService {

  private RestOperations rest;

  public AddressService(RestOperations rest) {
    this.rest = rest;
  }

  @HystrixCommand(fallbackMethod = "getAddressFallback")
  public Address getAddressFromService(Shipment shipment) {
    return rest.getForObject(
        "//accounts/accounts/" + shipment.getAccountId() +
            "/addresses/" + shipment.getShippingAddressId(), Address.class);
  }

  public Address getAddressFallback(Shipment shipment, Throwable t) {
    Address a = new Address();
    a.setId(shipment.getShippingAddressId());

    return a;
  }
}
