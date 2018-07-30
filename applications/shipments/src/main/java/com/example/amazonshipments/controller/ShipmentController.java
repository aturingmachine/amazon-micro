package com.example.amazonshipments.controller;

import com.example.amazonshipments.model.Shipment;
import com.example.amazonshipments.repository.ShipmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

  private ShipmentRepository shipments;

  public ShipmentController(ShipmentRepository shipments) {
    this.shipments = shipments;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Shipment createShipment(@RequestBody Shipment shipment) {
    return shipments.save(shipment);
  }

  @GetMapping("/{id}")
  public Shipment getOneShipment(@PathVariable("id") Long id) {
    return shipments.findById(id).get();
  }

  @GetMapping("/accounts/{id}")
  public List<Shipment> getShipmentsForAccount(@PathVariable("id") Long id) {
    return shipments.findByAccountId(id);
  }

  @PutMapping("/{id}")
  public Shipment updateShipment(@PathVariable("id") Long id, @RequestBody Shipment shipment) {
    Shipment shipmentToUpdate = shipments.findById(id).get();
    shipmentToUpdate.setDeliveredDate(shipment.getDeliveredDate());
    shipmentToUpdate.setShippedDate(shipment.getShippedDate());
    shipmentToUpdate.setShippingAddressId(shipment.getShippingAddressId());

    return shipments.save(shipmentToUpdate);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteShipment(@PathVariable("id") Long id) {
    shipments.deleteById(id);
  }
}
