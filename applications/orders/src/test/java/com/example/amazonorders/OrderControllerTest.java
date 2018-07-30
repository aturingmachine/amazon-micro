package com.example.amazonorders;

import com.example.amazonorders.controller.OrderController;
import com.example.amazonorders.model.Order;
import com.example.amazonorders.repository.OrderRepository;
import com.example.amazonorders.service.OrderService;
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

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SecurityException.class})
@WebMvcTest
public class OrderControllerTest {

  private MockMvc mvc;
  private String orderJSON;

  @InjectMocks
  private OrderController controller;

  @Mock
  private OrderService orders;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    this.orderJSON = "{" +
        " \"accountId\": 1," +
        " \"shippingAddressId\": 1," +
        " \"orderNumber\": \"123ABC\"," +
        " \"orderDate\": \"2018-07-25\"" +
        " }";
  }

  @Test
  public void testControllerNotNull() {
    assertNotNull(controller);
  }

  @Test
  public void testCreateOrder() throws Exception {
    mvc.perform(post("/orders")
        .contentType(MediaType.APPLICATION_JSON).content(orderJSON))
        .andExpect(status().isCreated())
        .andDo(print());
  }

  @Test
  public void testGetOrders() throws Exception {
    mvc.perform(get("/orders/account/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetOneOrder() throws Exception {
    when(orders.getOne(anyLong())).thenReturn(new Order());

    mvc.perform(get("/orders/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateOrder() throws Exception {
    when(orders.getOne(anyLong())).thenReturn(new Order());
    when(orders.save(any())).thenReturn(new Order());

    mvc.perform(put("/orders/1")
    .contentType(MediaType.APPLICATION_JSON).content(orderJSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteOrder() throws Exception {
    mvc.perform(delete("/orders/1"))
        .andExpect(status().isNoContent());
  }
}
