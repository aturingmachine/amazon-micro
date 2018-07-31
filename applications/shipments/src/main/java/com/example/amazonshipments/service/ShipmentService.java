package com.example.amazonshipments.service;

import com.example.amazonshipments.model.Shipment;
import com.example.amazonshipments.model.ShipmentPresenter;
import com.example.amazonshipments.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ShipmentService {

  private ShipmentRepository shipmentRepository;

  private LineItemRestService lineItemRest;

  private AddressService addresses;

  public ShipmentService(ShipmentRepository shipmentRepository, LineItemRestService lineItemRest,
      AddressService addresses) {

    this.shipmentRepository = shipmentRepository;
    this.lineItemRest = lineItemRest;
    this.addresses = addresses;
  }

  public Shipment save(Shipment shipment) {
    return shipmentRepository.save(shipment);
  }

  public ShipmentPresenter findById(Long id) {
    Shipment shipment = shipmentRepository.findById(id).get();

    ShipmentPresenter details = new ShipmentPresenter(shipment);

    details.setLineItems(Arrays.asList(lineItemRest.getLineItemsForShipment(shipment.getId())));
    details.setShippingAddress(addresses.getAddressFromService(shipment));

    return details;
  }

  public List<ShipmentPresenter> findByAccountId(Long accountId) {
    List<ShipmentPresenter> detailsList = new ArrayList<>();

    List<Shipment> shipments = shipmentRepository.findByAccountIdOrderByDeliveredDateAsc(accountId);

    shipments.forEach(shipment -> {
      ShipmentPresenter s = new ShipmentPresenter(shipment);
      s.setShippingAddress(addresses.getAddressFromService(shipment));
      s.setLineItems(Arrays.asList(lineItemRest.getLineItemsForShipment(shipment.getId())));
      detailsList.add(s);
    });

    return detailsList;
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
