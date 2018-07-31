package com.example.amazonshipments.service;

import com.example.amazonshipments.model.Address;
import com.example.amazonshipments.model.LineItem;
import com.example.amazonshipments.model.Shipment;
import com.example.amazonshipments.model.ShipmentPresenter;
import com.example.amazonshipments.repository.ShipmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ShipmentServiceTest {

  @Mock
  private ShipmentRepository shipments;

  @Mock
  private LineItemRestService lines;

  @Mock
  private AddressService addresses;

  @InjectMocks
  private ShipmentService service;

  private Shipment shipment;

  private LineItem[] items;

  private List<Shipment> shipmentList;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.shipment = new Shipment();
    this.items = new LineItem[1];
    this.shipmentList = new ArrayList<>();
    LineItem i = new LineItem();

    i.setQuantity(1);
    i.setPrice(10.0);
    i.setProductId(1L);
    i.setShipmentId(1L);
    items[0] = i;

    shipment.setId(1L);
    shipment.setShippingAddressId(1L);
    shipment.setShippedDate(new Date());
    shipment.setDeliveredDate(new Date());
    shipment.setAccountId(1L);
//    shipment.setLineItems(items);

    shipmentList.add(shipment);
  }

  @Test
  public void testServiceSave() {
    when(shipments.save(any())).thenReturn(shipment);

    Shipment s = service.save(shipment);

    assertEquals(shipment.getId(), s.getId());
  }

  @Test
  public void testFindById() {
    when(shipments.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(shipment));
    when(lines.getLineItemsForShipment(anyLong())).thenReturn(items);
    when(addresses.getAddressFromService(any())).thenReturn(new Address());

    ShipmentPresenter s = service.findById(1L);

    assertEquals(shipment.getId(), s.getId());
  }

  @Test
  public void testFindByAccountId() {
    when(shipments.findByAccountIdOrderByDeliveredDateAsc(anyLong()))
        .thenReturn(shipmentList);
    when(lines.getLineItemsForShipment(anyLong())).thenReturn(items);
    when(addresses.getAddressFromService(any())).thenReturn(new Address());


    List<ShipmentPresenter> l = service.findByAccountId(1L);

    assertEquals(l.get(0).getId(), shipment.getId());
  }

  @Test
  public void testUpdateShipment() {
    Shipment newShip = new Shipment();
    newShip.setId(2L);
    when(shipments.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(shipment));

    when(shipments.save(any())).thenReturn(newShip);

    Shipment s = service.updateShipment(1L, shipment);

    assertEquals(s.getId(), newShip.getId());
  }
}
