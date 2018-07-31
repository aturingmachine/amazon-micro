package com.example.amazonorders.service;

import com.example.amazonorders.model.Address;
import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.Shipment;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class CrossOriginRestService {

  private RestOperations rest;

  public CrossOriginRestService(RestOperations rest) {
    this.rest = rest;
  }


  public void checkIfCrossResourceExists(String resourceType, Long id) {
    String url = "//" + resourceType + "/" + resourceType + "/" + id;
    rest.getForEntity(url, String.class);
  }

  public Double getProductPrice(Long id) {
    return rest.getForObject("//products/products/" + id + "/price",
        Double.class);
  }

  @HystrixCommand(fallbackMethod = "getAddressFromServiceFallback")
  public Address getAddressFromService(Order o) {
    return rest.getForObject(
        "//accounts/accounts/" + o.getAccountId() + "/addresses/" + o.getShippingAddressId(),
        Address.class);
  }

  //Fallback for Address, returns Address model with just an ID
  public Address getAddressFromServiceFallback(Order o, Throwable t) {
    Address a = new Address();
    a.setId(o.getShippingAddressId());
    return a;
  }

  @HystrixCommand(fallbackMethod = "getShipmentFromServiceFallback")
  public Shipment getShipmentFromService(Long id) {
    return rest.getForObject("//shipments/shipments/" + id, Shipment.class);
  }

  public Shipment getShipmentFromServiceFallback(Long id, Throwable t) {
    t.printStackTrace();
    System.out.println("**********");
    System.out.println("FALLING BACK");
    Shipment s = new Shipment();
    s.setId(id);
    return s;
  }
}
