package com.example.accounts.controller;

import com.example.accounts.config.SecurityConfiguration;
import com.example.accounts.model.Account;
import com.example.accounts.repository.AccountRepository;
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

@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SecurityConfiguration.class})
public class AccountControllerTest {

  private MockMvc mvc;

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountController controller;

  private String accountJson;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    this.accountJson = "{ \"firstName\": \"test\"," +
        " \"lastName\": \"post\"," +
        " \"email\": \"test@test.com\" }";
  }

  @Test
  public void testControllerIsNotNull() {
    assertNotNull(controller);
  }

  @Test
  public void testCreateAccount() throws Exception {
    mvc.perform(post("/accounts")
        .contentType(MediaType.APPLICATION_JSON).content(accountJson))
    .andExpect(status().isCreated())
    .andDo(print());
  }

  @Test
  public void testGetAccounts() throws Exception {
    mvc.perform(get("/accounts"))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void testUpdateAccount() throws Exception {
    when(accountRepository.save(any())).thenReturn(new Account());
    when(accountRepository.findById(anyLong())).thenReturn(java.util.Optional.of(new Account()));

    mvc.perform(put("/accounts/1")
        .contentType(MediaType.APPLICATION_JSON).content(accountJson))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void testDeleteAccount() throws Exception {
    mvc.perform(delete("/accounts/1"))
        .andExpect(status().isNoContent())
        .andDo(print());
  }
}
