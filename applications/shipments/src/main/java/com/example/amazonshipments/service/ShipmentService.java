package com.example.amazonshipments.service;

import com.example.amazonshipments.model.LineItem;
import com.example.amazonshipments.model.Shipment;
import com.example.amazonshipments.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;
import java.util.List;

@Service
public class ShipmentService {

  private ShipmentRepository shipmentRepository;

  private RestOperations rest;

  public ShipmentService(ShipmentRepository shipmentRepository, RestOperations rest) {
    this.shipmentRepository = shipmentRepository;
    this.rest = rest;
  }

  public Shipment save(Shipment shipment) {
    return shipmentRepository.save(shipment);
  }

  public Shipment findById(Long id) {
    Shipment shipment = shipmentRepository.findById(id).get();
    LineItem[] list = rest.getForObject("//orders/orders/0/lines/shipments/" + id, LineItem[].class);
    shipment.setLineItems(Arrays.asList(list));

    return shipment;
  }

  public List<Shipment> findByAccountId(Long accountId) {
    List<Shipment> shipments = shipmentRepository.findByAccountIdOrderByDeliveredDateAsc(accountId);

    shipments.forEach(shipment -> {
      LineItem[] list = rest.getForObject("//orders/orders/0/lines/shipments/" + shipment.getId(),
          LineItem[].class);

      shipment.setLineItems(Arrays.asList(list));
    });

    return shipments;
  }

  public Shipment updateShipment(Long shipmentId, Shipment shipment) {
    Shipment shipmentToUpdate = shipmentRepository.findById(shipmentId).get();
    shipmentToUpdate.setDeliveredDate(shipment.getDeliveredDate());
    shipmentToUpdate.setShippedDate(shipment.getShippedDate());
    shipmentToUpdate.setShippingAddressId(shipment.getShippingAddressId());

    return shipmentRepository.save(shipmentToUpdate);
  }

  public void deleteById(Long id) {
    shipmentRepository.deleteById(id);
  }
}
