package com.example.amazonorders;

import com.example.amazonorders.model.Address;
import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.Shipment;
import com.example.amazonorders.service.CrossOriginRestService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class CrossOriginResourceTest {

  @Mock
  private RestOperations rest;

  @InjectMocks
  private CrossOriginRestService service;

  private Double d;

  private Address a;

  private Order o;

  private Shipment s;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.d = 10.0;
    this.a = new Address();
    a.setId(1L);
    this.o = new Order();
    o.setId(1L);
    o.setShippingAddressId(10L);
    this.s = new Shipment();
    s.setId(4L);
  }

  @Test
  public void testGetProductPrice() {
    when(rest.getForObject(anyString(), eq(Double.class))).thenReturn(d);

    Double result = service.getProductPrice(1L);

    assertEquals(result, d);
  }

  @Test
  public void testGetAddressFromService() {
    when(rest.getForObject(anyString(), eq(Address.class)))
        .thenReturn(a);

    Address address = service.getAddressFromService(o);

    assertEquals(address.getId(), a.getId());
  }

  @Test
  public void testGetAddressFromServiceFallback() {
    when(rest.getForObject(anyString(), eq(Address.class)))
        .thenReturn(service.getAddressFromServiceFallback(o, new Throwable()));

    Address address = service.getAddressFromService(o);

    assertEquals(address.getId(), o.getShippingAddressId());
  }

  @Test
  public void testGetShipmentFromService() {
    when(rest.getForObject(anyString(), eq(Shipment.class)))
        .thenReturn(s);

    Shipment shipment = service.getShipmentFromService(1L);

    assertEquals(shipment.getId(), s.getId());
  }

  @Test
  public void testGetShipmentFromServiceFallback() {
    when(rest.getForObject(anyString(), eq(Shipment.class)))
        .thenReturn(service.getShipmentFromServiceFallback(4L, new Throwable()));

    Shipment shipment = service.getShipmentFromService(1L);

    assertEquals(shipment.getId(), s.getId());
  }

  @Test
  public void testCrossResourceCheck() {
    when(rest.getForEntity(anyString(), eq(String.class)))
        .thenReturn(new ResponseEntity<String>(HttpStatus.OK));
    
    service.checkIfCrossResourceExists("test", 1L);
  }
}
