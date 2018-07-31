package com.example.amazonproducts.controller;

import com.example.amazonproducts.config.SecurityConfiguration;
import com.example.amazonproducts.model.Product;
import com.example.amazonproducts.repository.ProductRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SecurityConfiguration.class})
public class ProductControllerTest {

  private MockMvc mvc;
  private String productJSON;

  @InjectMocks
  private ProductController controller;

  @Mock
  private ProductRepository products;

  private Product p;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    this.mvc = MockMvcBuilders.standaloneSetup(controller).build();

    this.productJSON = "{ " +
        " \"name\": \"test\"," +
        " \"description\": \"test\"," +
        " \"price\": 100.0," +
        " \"image\": \"test\"" +
        " }";

    this.p = new Product();
    p.setPrice(10.0);
  }

  @Test
  public void testControllerNotNull() {
    assertNotNull(controller);
  }

  @Test
  public void testCreateProduct() throws Exception {
    mvc.perform(post("/products")
    .contentType(MediaType.APPLICATION_JSON).content(productJSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void testGetAllProducts() throws Exception {
    mvc.perform(get("/products"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetOneProduct() throws Exception {
    when(products.findById(anyLong())).thenReturn(java.util.Optional.of(new Product()));

    mvc.perform(get("/products/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateProduct() throws Exception {
    when(products.save(any())).thenReturn(new Product());
    when(products.findById(anyLong())).thenReturn(java.util.Optional.of(new Product()));

    mvc.perform(put("/products/1")
    .contentType(MediaType.APPLICATION_JSON).content(productJSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteProduct() throws Exception {
    mvc.perform(delete("/products/1")).andExpect(status().isNoContent());
  }

  @Test
  public void testGetPrice() throws Exception {
    when(products.findById(any())).thenReturn(java.util.Optional.ofNullable(p));

    mvc.perform(get("/products/1/price"))
        .andExpect(status().isOk())
        .andDo(result ->
            assertEquals(result.getResponse().getContentAsString(), p.getPrice().toString()));
  }
}
