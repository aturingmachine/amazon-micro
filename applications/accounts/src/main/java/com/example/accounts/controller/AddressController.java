package com.example.accounts.controller;

import com.example.accounts.model.Address;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.repository.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/addresses")
public class AddressController {

  private AddressRepository addressRepository;
  private AccountRepository accountRepository;

  public AddressController(AddressRepository addressRepository, AccountRepository accountRepository) {
    this.addressRepository = addressRepository;
    this.accountRepository = accountRepository;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Address createAddress(@PathVariable("accountId") Long id, @RequestBody Address address) {
    address.setAccount(accountRepository.findById(id).get());
    return addressRepository.save(address);
  }

  @GetMapping("")
  public List<Address> getAddresses(@PathVariable("accountId") Long id) {
    return addressRepository.findByAccountId(id);
  }

  @GetMapping("/{addressId}")
  public Address getOneAddress(@PathVariable("addressId") Long id) {
    return addressRepository.findById(id).get();
  }

  @PutMapping("/{addressId}")
  public Address updateAddress(@PathVariable("accountId") Long accountId,
      @PathVariable("addressId") Long addressId, @RequestBody Address address) {

    Address addressToUpdate = addressRepository.findById(addressId).get();

    addressToUpdate.setBuilding(address.getBuilding());
    addressToUpdate.setCity(address.getCity());
    addressToUpdate.setCountry(address.getCountry());
    addressToUpdate.setState(address.getState());
    addressToUpdate.setStreet(address.getStreet());
    addressToUpdate.setZip(address.getZip());

    return addressRepository.save(addressToUpdate);
  }

  @DeleteMapping("/{addressId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAddress(@PathVariable("addressId") Long id) {
    addressRepository.deleteById(id);
  }
}
