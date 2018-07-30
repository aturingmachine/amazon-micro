package com.example.accounts.controller;

import com.example.accounts.model.Account;
import com.example.accounts.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private AccountRepository accounts;

  public AccountController(AccountRepository accounts) {

    this.accounts = accounts;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Account createAccount(@RequestBody Account account) {
    return accounts.save(account);
  }

  @GetMapping("")
  public List<Account> getAccounts() {
    return accounts.findAll();
  }

  @PutMapping("/{id}")
  public Account updateAccount(@PathVariable("id") Long id, @RequestBody Account account) {
    Account accountToUpdate = accounts.findById(id).get();
    accountToUpdate.setEmail(account.getEmail());
    accountToUpdate.setFirstName(account.getFirstName());
    accountToUpdate.setLastName(account.getLastName());

    return accounts.save(accountToUpdate);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAccount(@PathVariable("id") Long id) {
    accounts.deleteById(id);
  }
}
