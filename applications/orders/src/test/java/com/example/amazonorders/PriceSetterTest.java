package com.example.amazonorders;


import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.OrderLineItem;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class PriceSetterTest {

  private Order order;

  private List<OrderLineItem> items = new ArrayList<>();

  @Before
  public void setup() {
    this.order = new Order();
    OrderLineItem item1 = new OrderLineItem();
    OrderLineItem item2 = new OrderLineItem();

    item1.setPrice(5.0);
    item1.setQuantity(2);
    items.add(item1);

    item2.setPrice(25.0);
    item2.setQuantity(1);
    items.add(item2);

    order.setLineItems(items);
  }


  @Test
  public void testOrderGetTotalPrice() {
    assertEquals(Double.valueOf(35.0), order.getTotalPrice());
  }

  @Test
  public void testLineItemSetTotalPrice() {
    assertEquals(Double.valueOf(10.0), items.get(0).getTotalPrice());
  }

  @Test
  public void testLineItemSetTotalAfterChange() {
    items.get(0).setQuantity(4);
    assertEquals(Double.valueOf(20.0), items.get(0).getTotalPrice());
  }
}
