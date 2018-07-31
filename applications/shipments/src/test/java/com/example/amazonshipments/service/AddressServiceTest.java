package com.example.amazonshipments.service;

import com.example.amazonshipments.model.Address;
import com.example.amazonshipments.model.Shipment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestOperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class AddressServiceTest {

  @Mock
  private RestOperations rest;

  @InjectMocks
  private AddressService service;

  private Address address;

  private Shipment s;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.address = new Address();
    address.setId(2L);
    address.setBuilding("test");
    address.setCity("test");
    address.setCountry("test");
    address.setState("test");
    address.setStreet("test");
    address.setZip("test lane");

    this.s = new Shipment();

    s.setId(1L);
    s.setShippingAddressId(1L);
  }

  @Test
  public void testServiceNotNull() {
    assertNotNull(service);
  }

  @Test
  public void testGetAddress() {
    when(rest.getForObject(anyString(), eq(Address.class))).thenReturn(address);

    Address a = service.getAddressFromService(s);

    assertEquals(a.getCountry(), address.getCountry());
    assertEquals(a.getId(), Long.valueOf(2));
  }

  @Test
  public void testGetAddressFallback() {
    when(rest.getForObject(anyString(), any())).thenReturn(
        service.getAddressFallback(s, new Throwable()));

    Address a = service.getAddressFromService(s);

    assertEquals(a.getId(), Long.valueOf(1));
  }
}
