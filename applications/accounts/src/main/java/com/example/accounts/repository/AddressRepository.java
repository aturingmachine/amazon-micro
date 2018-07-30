package com.example.accounts.repository;

import com.example.accounts.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

  List<Address> findByAccountId(Long id);
}
