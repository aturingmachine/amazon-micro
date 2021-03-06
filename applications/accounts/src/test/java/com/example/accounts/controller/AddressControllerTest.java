package com.example.accounts.controller;

import com.example.accounts.config.SecurityConfiguration;
import com.example.accounts.model.Account;
import com.example.accounts.model.Address;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.repository.AddressRepository;
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

@WebMvcTest(AddressController.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SecurityConfiguration.class})
public class AddressControllerTest {

  private MockMvc mvc;

  @Mock
  private AddressRepository addresses;

  @Mock
  private AccountRepository accounts;

  @InjectMocks
  private AddressController controller;

  private Address a = new Address();

  private Account account = new Account();

  private String addressJSON;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    this.mvc = MockMvcBuilders.standaloneSetup(controller).build();

    this.addressJSON = "{ " +
        "\"street\": \"test\"," +
        " \"building\": \"test\", " +
        "\"city\": \"test\"," +
        "\"state\": \"test\"," +
        "\"zip\": \"test\"," +
        "\"country\": \"test\"" +
        " }";

    account.setId(1L);
    a.setZip("12345");
    a.setAccount(account);
  }

  @Test
  public void testControllerIsNotNull() {
    assertNotNull(controller);
  }

  @Test
  public void testCreateAddress() throws Exception {
    when(accounts.findById(anyLong())).thenReturn(java.util.Optional.of(new Account()));
    mvc.perform(post("/accounts/1/addresses")
    .contentType(MediaType.APPLICATION_JSON).content(addressJSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void testGetAddress() throws Exception {
    mvc.perform(get("/accounts/1/addresses"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetOneAddress() throws Exception {
    when(addresses.findById(anyLong())).thenReturn(java.util.Optional.of(a));
    mvc.perform(get("/accounts/1/addresses/1"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateAddress() throws Exception {
    when(addresses.save(any())).thenReturn(a);
    when(addresses.findById(anyLong())).thenReturn(java.util.Optional.of(a));

    mvc.perform(put("/accounts/1/addresses/1")
    .contentType(MediaType.APPLICATION_JSON).content(addressJSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteAddress() throws Exception {
    mvc.perform(delete("/accounts/1/addresses/1"))
        .andExpect(status().isNoContent());
  }
}
