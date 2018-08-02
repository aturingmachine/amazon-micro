package com.example.amazonorders;

import com.example.amazonorders.model.Address;
import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.OrderLineItem;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

  private OrderLineItem i = new OrderLineItem();

  private List<OrderLineItem> items = new ArrayList<>();

  private Order o;

  private Shipment s;

  private Date now;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.d = 10.0;
    this.a = new Address();
    now = new Date();

    a.setId(1L);
    a.setBuilding("test");
    a.setCity("test");
    a.setCountry("test");
    a.setState("test");
    a.setStreet("test");
    a.setZip("12345");
    this.o = new Order();
    o.setId(1L);
    o.setShippingAddressId(10L);
    this.s = new Shipment();
    s.setId(4L);
    s.setDeliveredDate(now);
    s.setShippedDate(now);
    s.setLineItems(items);
    i.setOrder(o);
    i.setShipmentId(s.getId());
    i.setProductId(1L);
    i.setQuantity(2);
    items.add(i);
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
    assertEquals(address.getBuilding(), a.getBuilding());
    assertEquals(address.getCity(), a.getCity());
    assertEquals(address.getCountry(), a.getCountry());
    assertEquals(address.getState(), a.getState());
    assertEquals(address.getStreet(), a.getStreet());
    assertEquals(address.getZip(), a.getZip());
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
    assertEquals(shipment.getDeliveredDate(), s.getDeliveredDate());
    assertEquals(shipment.getShippedDate(), s.getShippedDate());
    assertEquals(shipment.getLineItems().get(0).getQuantity(), i.getQuantity());
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
