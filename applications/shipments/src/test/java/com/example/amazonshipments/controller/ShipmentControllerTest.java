package com.example.amazonshipments.controller;

import com.example.amazonshipments.config.SecurityConfiguration;
import com.example.amazonshipments.model.Shipment;
import com.example.amazonshipments.model.ShipmentPresenter;
import com.example.amazonshipments.repository.ShipmentRepository;
import com.example.amazonshipments.service.ShipmentService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SecurityConfiguration.class})
public class ShipmentControllerTest {

  private MockMvc mvc;
  private String shipmentJson;

  @InjectMocks
  private ShipmentController controller;

  @Mock
  private ShipmentService shipments;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    this.shipmentJson = "{" +
        " \"accountId\": 1," +
        " \"shippingAddressId\": 1," +
        " \"shippedDate\": \"2018-06-22\"," +
        " \"deliveredDate\": \"2018-06-23\"" +
        "}";
  }

  @Test
  public void testControllerNotNull() {
    assertNotNull(controller);
  }

  @Test
  public void testCreateShipment() throws Exception {
    mvc.perform(post("/shipments")
    .contentType(MediaType.APPLICATION_JSON).content(shipmentJson))
        .andExpect(status().isCreated());
  }

  @Test
  public void testGetOneShipment() throws Exception {
    when(shipments.findById(anyLong())).thenReturn(new ShipmentPresenter());

    mvc.perform(get("/shipments/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetAllShipmentsForAccount() throws Exception {
    mvc.perform(get("/shipments/accounts/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateShipment() throws Exception {
    when(shipments.findById(anyLong())).thenReturn(new ShipmentPresenter());

    mvc.perform(put("/shipments/1")
    .contentType(MediaType.APPLICATION_JSON).content(shipmentJson))
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteShipment() throws Exception {
    mvc.perform(delete("/shipments/1"))
        .andExpect(status().isNoContent());
  }
}
