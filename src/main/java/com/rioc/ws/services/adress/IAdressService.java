package com.rioc.ws.services.adress;

import com.rioc.ws.modeles.dto.AccountDto;
import com.rioc.ws.modeles.dto.AdressDto;

public interface IAdressService {
    public AdressDto validAdress(AccountDto accountDto);
}
