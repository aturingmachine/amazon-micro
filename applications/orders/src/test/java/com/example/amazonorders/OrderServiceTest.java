package com.example.amazonorders;

import com.example.amazonorders.model.Order;
import com.example.amazonorders.repository.OrderRepository;
import com.example.amazonorders.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @InjectMocks
  private OrderService service;

  private Order order = new Order();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    order.setShippingAddressId((long) 1);
    order.setOrderNumber("12345");
    order.setAccountId((long) 1);
    order.setOrderDate(new Date());
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
