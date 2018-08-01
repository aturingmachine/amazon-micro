package com.example.amazonorders;

import com.example.amazonorders.model.*;
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

  private OrderDetails details;

  private List<Order> orderList = new ArrayList<>();

  private List<OrderLineItem> items = new ArrayList<>();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    this.order.setShippingAddressId((long) 1);
    this.order.setOrderNumber("12345");
    this.order.setAccountId(1L);
    this.order.setOrderDate(new Date());

    OrderLineItem i = new OrderLineItem();

    items.add(i);
    this.order.setLineItems(items);

    details = new OrderDetails(order);

    orderList.add(order);
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
    when(cross.getAddressFromService(any())).thenReturn(new Address());

    service.getOne((long) 1);

    assertEquals(order.getAccountId(), Long.valueOf(1));
  }

  @Test
  public void testGetAllOrders() {
    when(orderRepository.findById(any()))
        .thenReturn(java.util.Optional.of(order));
    when(orderRepository.findByAccountIdOrderByOrderDateAsc(anyLong()))
        .thenReturn(orderList);
    when(cross.getAddressFromService(any())).thenReturn(new Address());

    List<OrderDetails> list = service.getAllOrdersForAccount(1L);

    assertEquals(list.get(0).getOrderNumber(), order.getOrderNumber());
  }

  @Test
  public void testUpdateOrder() {
    when(orderRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(order));

    Order o = order;
    o.setAccountId((long) 2);

    service.updateOrder((long) 1, order);

    assertEquals(order.getAccountId(), Long.valueOf(2));
  }

  @Test
  public void testDelete() {
    service.deleteOrder(order.getId());
  }

}
