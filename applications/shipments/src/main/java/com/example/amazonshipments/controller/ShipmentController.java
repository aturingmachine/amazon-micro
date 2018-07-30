package com.example.amazonshipments.controller;

import com.example.amazonshipments.model.Shipment;
import com.example.amazonshipments.repository.ShipmentRepository;
import com.example.amazonshipments.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

  private ShipmentService shipments;

  public ShipmentController(ShipmentService shipments) {
    this.shipments = shipments;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Shipment createShipment(@RequestBody Shipment shipment) {
    return shipments.save(shipment);
  }

  @GetMapping("/{id}")
  public Shipment getOneShipment(@PathVariable("id") Long id) {
    return shipments.findById(id);
  }

  @GetMapping("/accounts/{id}")
  public List<Shipment> getShipmentsForAccount(@PathVariable("id") Long id) {
    return shipments.findByAccountId(id);
  }

  @PutMapping("/{id}")
  public Shipment updateShipment(@PathVariable("id") Long id, @RequestBody Shipment shipment) {
    return shipments.updateShipment(id, shipment);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteShipment(@PathVariable("id") Long id) {
    shipments.deleteById(id);
  }
}
