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

import com.rioc.ws.modeles.dao.BankDetail;
import com.rioc.ws.modeles.dto.BankDetailDto;
import com.rioc.ws.services.bankDetail.IBankDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Banks Documentation")
public class BankDetailController {
    
    private IBankDetailService service;

    public BankDetailController(IBankDetailService service){
        this.service = service;
    }

    @ApiOperation(value = "Add Bank Detail", response = BankDetailDto.class, notes = "Account mustn't exist")
    @PostMapping("/bankDetail")
    public ResponseEntity<BankDetail> save(@Valid @RequestBody BankDetailDto bankDetailDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.postBankDetail(bankDetailDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Gets Bank Details by pseudo", response = BankDetailDto.class, notes = "Pseudo must exist")
    @GetMapping("/bankDetail/byPseudo")
    public ResponseEntity<List<BankDetailDto>> getBankDetailByPseudo(@RequestParam("pseudo") String pseudo) {
        return new ResponseEntity<>(service.getByPseudo(pseudo), HttpStatus.FOUND);
    }

    @ApiOperation(value = "Delete bank detail by iban", response = BankDetailDto.class, notes = "Iban must exist")
    @DeleteMapping("/bankDetail/byIban")
    public ResponseEntity<BankDetailDto> deleteByIban(@RequestParam("iban") String iban) {
        return new ResponseEntity<>(service.deleteByIban(iban), HttpStatus.ACCEPTED);
    }

}
