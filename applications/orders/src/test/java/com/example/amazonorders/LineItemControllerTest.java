package com.example.amazonorders;

import com.example.amazonorders.config.SecurityConfiguration;
import com.example.amazonorders.controller.LineItemController;
import com.example.amazonorders.model.Order;
import com.example.amazonorders.model.OrderLineItem;
import com.example.amazonorders.repository.LineItemRepository;
import com.example.amazonorders.service.LineItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SecurityConfiguration.class})
public class LineItemControllerTest {

  private MockMvc mvc;

  private String lineItemJSON;

  private OrderLineItem item = new OrderLineItem();

  private Order o = new Order();

  @InjectMocks
  private LineItemController controller;

  @Mock
  private LineItemService lineItems;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    this.lineItemJSON = "{" +
        " \"productId\": 1," +
        " \"quantity\": 4," +
        " \"shipmentId\": 1" +
        " }";

    o.setId(1L);
    item.setOrder(o);
  }

  @Test
  public void testControllerNotNull() {
    assertNotNull(controller);
  }

  @Test
  public void testCreateLineItem() throws Exception {
    mvc.perform(post("/orders/1/lines")
    .contentType(MediaType.APPLICATION_JSON).content(lineItemJSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void testGetOneLineItem() throws Exception {
    when(lineItems.getOneLineItem(anyLong(), anyLong())).thenReturn(item);
    mvc.perform(get("/orders/1/lines/1"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testGetByShipmentId() throws Exception {
    mvc.perform(get("/orders/1/lines/shipments/1"))
        .andExpect(status().isOk())
        .andDo(result -> assertEquals(0, result.getResponse().getContentLength()));
  }

  @Test
  public void testUpdateLineItem() throws Exception {
    when(lineItems.getOneLineItem(anyLong(), anyLong())).thenReturn(new OrderLineItem());
    mvc.perform(put("/orders/1/lines/1")
    .contentType(MediaType.APPLICATION_JSON).content(lineItemJSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteLineItem() throws Exception {
    mvc.perform(delete("/orders/1/lines/1"))
        .andExpect(status().isNoContent());
  }
}
