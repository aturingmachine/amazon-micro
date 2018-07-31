package com.example.amazonshipments.service;

import com.example.amazonshipments.model.LineItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestOperations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LineItemRestServiceTest {

  @Mock
  private RestOperations rest;

  @InjectMocks
  private LineItemRestService service;

  private LineItem[] items;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    this.items = new LineItem[1];

    LineItem i = new LineItem();
    i.setShipmentId(1L);
    i.setProductId(1L);
    i.setPrice(10.0);
    i.setQuantity(2);
    i.setTotalPrice(20.0);

    items[0] = i;
  }

  @Test
  public void testGetLineItemForShipmentNoFallBack() {
    when(rest.getForObject(anyString(), any())).thenReturn(items);

    LineItem[] list = service.getLineItemsForShipment(1L);

    assertEquals(list[0].getProductId(), Long.valueOf(1));
  }

  @Test
  public void testGetLineItemForShipmentWithFallBack() {
    when(rest.getForObject(anyString(), any())).thenReturn(
        service.fallBackGetLineItemsForShipment(1L, new Throwable())
    );

    LineItem[] l = service.getLineItemsForShipment(1L);

    assertEquals(l[0].getQuantity(), Integer.valueOf(-1));
  }
}
