package com.example.amazonorders;


import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.OrderLineItem;
import com.example.amazonorders.repository.LineItemRepository;
import com.example.amazonorders.repository.OrderRepository;
import com.example.amazonorders.service.CrossOriginRestService;
import com.example.amazonorders.service.LineItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LineItemServiceTest {

  @Mock
  private LineItemRepository items;

  @Mock
  private OrderRepository orders;

  @Mock
  private CrossOriginRestService rest;

  private OrderLineItem lineItem = new OrderLineItem();
  private OrderLineItem newLineItem = new OrderLineItem();

  @InjectMocks
  private LineItemService service;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.lineItem.setShipmentId((long) 1);
    this.lineItem.setProductId((long) 1);
    this.lineItem.setQuantity(2);
    this.lineItem.setPrice(10.0);

    this.newLineItem.setShipmentId(2L);
    this.newLineItem.setProductId(2L);
    this.newLineItem.setQuantity(2);
    this.newLineItem.setPrice(15.0);
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

  @Test
  public void testUpdate() {
    when(orders.findById(anyLong())).thenReturn(java.util.Optional.of(new Order()));
    when(items.findById(anyLong())).thenReturn(java.util.Optional.of(lineItem));
    when(items.save(any())).thenReturn(newLineItem);
    when(rest.getProductPrice(anyLong())).thenReturn(15.0);

    OrderLineItem i = service.updateItem(1L, 1L, lineItem);

    assertEquals(i.getQuantity(), newLineItem.getQuantity());
  }

  @Test
  public void testSave() {
    when(orders.findById(anyLong())).thenReturn(java.util.Optional.of(new Order()));
    when(items.save(any())).thenReturn(lineItem);

    OrderLineItem i = service.save(1L, lineItem);

    assertEquals(i.getQuantity(), lineItem.getQuantity());
  }

  @Test
  public void testFailOnOrderExists() {
    when(orders.findById(anyLong())).thenReturn(java.util.Optional.empty());
    when(items.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(lineItem));

    try {
      service.getOneLineItem(1L, 1L);
    } catch (NoSuchElementException e) {
      assertEquals(e.getClass(), NoSuchElementException.class);
    }
  }
}
