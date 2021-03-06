package com.example.amazonorders.exception;

import com.example.amazonorders.config.SecurityConfiguration;
import com.example.amazonorders.controller.OrderController;
import com.example.amazonorders.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.net.ConnectException;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SecurityConfiguration.class})
public class RestExceptionHandlerTest {

  private MockMvc mvc;

  private RestExceptionHandler handler;

  @Mock
  private OrderService orders;

  @InjectMocks
  private OrderController controller;


  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.handler = new RestExceptionHandler();
    this.mvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(handler)
        .build();
  }

  @Test
  public void testUnreadableError() throws Exception {
    when(orders.getOne(any())).thenThrow(new HttpMessageNotReadableException("test"));

    mvc.perform(get("/orders/1")).andExpect(status().isBadRequest());
  }

  @Test
  public void testNotFoundExceptionViaNoSuchElement() throws Exception {
    when(orders.getOne(any())).thenThrow(new NoSuchElementException());

    mvc.perform(get("/orders/1")).andExpect(status().isNotFound());
  }

  @Test
  public void testNotFoundExceptionViaEmptyResult() throws Exception {
    when(orders.getOne(any())).thenThrow(new EmptyResultDataAccessException(1));

    mvc.perform(get("/orders/1")).andExpect(status().isNotFound());
  }

  @Test
  public void testNotFoundExceptionViaEntityNotFound() throws Exception {
    when(orders.getOne(any())).thenThrow(new EntityNotFoundException());

    mvc.perform(get("/orders/1")).andExpect(status().isNotFound());
  }

  @Test
  public void testNotFoundExceptionViaHttpClientErrorException() throws Exception {
    when(orders.getOne(any())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

    mvc.perform(get("/orders/1")).andExpect(status().isNotFound());
  }

  @Test
  public void testFailedDependencyViaIllegalState() throws Exception {
    when(orders.getOne(any())).thenThrow(new IllegalStateException());

    mvc.perform(get("/orders/1")).andExpect(status().isFailedDependency());
  }
}
