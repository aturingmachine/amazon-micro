package com.example.amazonorders;


import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.OrderLineItem;
import com.example.amazonorders.repository.LineItemRepository;
import com.example.amazonorders.repository.OrderRepository;
import com.example.amazonorders.service.LineItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class LineItemServiceTest {

  @Mock
  private LineItemRepository items;

  @Mock
  private OrderRepository orders;

  private OrderLineItem lineItem = new OrderLineItem();

  @InjectMocks
  private LineItemService service;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.lineItem.setShipmentId((long) 1);
    this.lineItem.setProductId((long) 1);
    this.lineItem.setQuantity(2);
    this.lineItem.setOrderId((long) 1);
    this.lineItem.setPrice(10.0);
  }

  @Test
  public void testServiceNotNull() {
    assertNotNull(service);
  }

  @Test
  public void testGetOneLineItem() {
    when(orders.findById(anyLong())).thenReturn(java.util.Optional.of(new Order()));
    when(items.findById(anyLong())).thenReturn(java.util.Optional.of(new OrderLineItem()));

    OrderLineItem i = service.getOneLineItem((long) 1, (long) 1);

    assertNotNull(i);
  }

  @Test
  public void testGetByShipment() {
    when(items.findByShipmentId(anyLong())).thenReturn(new ArrayList<>());

    List<OrderLineItem> a = service.findByShipment((long) 1);

    assertNotNull(a);
  }

  @Test
  public void testDelete() {
    when(orders.findById(anyLong())).thenReturn(java.util.Optional.of(new Order()));

    service.deleteItem((long) 1, (long) 1);
  }
}
