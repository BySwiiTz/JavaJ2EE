package com.rioc.ws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rioc.ws.modeles.dao.Account;
import com.rioc.ws.modeles.dao.Adress;
import com.rioc.ws.modeles.dao.BankDetail;
import com.rioc.ws.modeles.dto.AccountDto;
import com.rioc.ws.modeles.dto.AdressDto;
import com.rioc.ws.modeles.dto.BankDetailDto;

@Mapper(componentModel = "spring")
public interface IAccountMapper {
    AccountDto accountToAccountDto (Account account);
    Account accountDtoToAccount (AccountDto accountDto);
    AdressDto adressToAdressDto (Adress adress);
    Adress adressDtoToAdress (AdressDto adressDto);
    @Mapping(target="pseudo", source="account.pseudo")
    BankDetailDto bankDetailToBankDetailDto (BankDetail bankDetail);
    BankDetail bankDetailDtoToBankDetail (BankDetailDto bankDetailDto);
}
