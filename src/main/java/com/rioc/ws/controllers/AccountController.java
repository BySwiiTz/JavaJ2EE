package com.rioc.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rioc.ws.modeles.dao.Account;
import com.rioc.ws.modeles.dto.AccountDto;
import com.rioc.ws.services.account.IAccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Accounts Documentation")
public class AccountController {

    private IAccountService service;

    public AccountController(IAccountService service) {
        this.service = service;
    }

    @ApiOperation(value = "Create account", response = AccountDto.class, notes = "Account mustn't exist")
    @PostMapping("/accounts")
    public ResponseEntity<Account> save(@Valid @RequestBody AccountDto account, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.postAccount(account), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Gets all accounts", response = AccountDto.class, notes = "Recommend having at least one account")
    @GetMapping("/accounts")
    public List<AccountDto> getAll() {
        return service.getAllAccount();
    }

    @ApiOperation(value = "Gets accounts by firstName", response = AccountDto.class, notes = "Account must exist")
    @GetMapping("/accounts/byFirstName")
    public ResponseEntity<List<AccountDto>> getByFirstName(@RequestParam("firstName") String firstName) {
        return new ResponseEntity<>(service.getByFirstName(firstName), HttpStatus.FOUND);
    }

    @ApiOperation(value = "Gets accounts by pseudo", response = AccountDto.class, notes = "Account must exist")
    @GetMapping("/accounts/byPseudo")
    public ResponseEntity<List<AccountDto>> getByPseudo(@RequestParam("pseudo") String pseudo) {
        return new ResponseEntity<>(service.getByPseudo(pseudo), HttpStatus.FOUND);
    }

    @ApiOperation(value = "Gets accounts by lastName", response = AccountDto.class, notes = "Account must exist")
    @GetMapping("/accounts/byLastName")
    public ResponseEntity<List<AccountDto>> getByLastName(@RequestParam("lastName") String lastName) {
        return new ResponseEntity<>(service.getByLastName(lastName), HttpStatus.FOUND);
    }

    @ApiOperation(value = "Delete accounts by firstName and lastName", response = AccountDto.class, notes = "Account must exist")
    @DeleteMapping("/accounts/byFirst&LastName")
    public ResponseEntity<Account> deleteAccount(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return new ResponseEntity<>(service.deleteAccount(firstName, lastName), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Delete accounts by pseudo", response = AccountDto.class, notes = "Account must exist")
    @DeleteMapping("/accounts/byPseudo")
    public ResponseEntity<Account> deleteByPseudo(@RequestParam("pseudo") String pseudo) {
        return new ResponseEntity<>(service.deleteByPseudo(pseudo), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Delete all accounts", response = AccountDto.class, notes = "Account must exist")
    @DeleteMapping("/accounts")
    public ResponseEntity<String> deleteAll() {
        return new ResponseEntity<>(service.deleteAll(), HttpStatus.ACCEPTED);
    }

}
