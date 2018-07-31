package com.example.amazonorders;

import com.example.amazonorders.model.Address;
import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.OrderLineItem;
import com.example.amazonorders.model.Shipment;
import com.example.amazonorders.repository.OrderRepository;
import com.example.amazonorders.service.CrossOriginRestService;
import com.example.amazonorders.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private RestTemplate rest;

  @Mock
  private CrossOriginRestService cross;

  @InjectMocks
  private OrderService service;

  private Order order = new Order();

  private List<OrderLineItem> items = new ArrayList<>();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    order.setShippingAddressId((long) 1);
    order.setOrderNumber("12345");
    order.setAccountId((long) 1);
    order.setOrderDate(new Date());

    OrderLineItem i = new OrderLineItem();

    items.add(i);
    order.setLineItems(items);
  }

  @Test
  public void testServiceNotNull() {
    assertNotNull(service);
  }

  @Test
  public void testServiceSave() {
    when(orderRepository.save(any())).thenReturn(order);

    service.save(order);

    assertEquals(order.getAccountId(), Long.valueOf(1));
  }

  @Test
  public void testGetOneOrder() {
    when(orderRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(order));
    when(rest.getForObject(anyString(), eq(Address.class))).thenReturn(new Address());
    when(rest.getForObject(anyString(), eq(Shipment.class))).thenReturn(new Shipment());
    when(cross.getAddressFromService(any())).thenReturn(new Address());
    service.getOne((long) 1);

    assertEquals(order.getAccountId(), Long.valueOf(1));
  }

  @Test
  public void testUpdateOrder() {
    when(orderRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(order));

    Order o = order;
    o.setAccountId((long) 2);

    service.updateOrder((long) 1, order);

    assertEquals(order.getAccountId(), Long.valueOf(2));
  }

}
