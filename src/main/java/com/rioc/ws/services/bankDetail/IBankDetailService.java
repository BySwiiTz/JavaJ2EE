package com.rioc.ws.services.bankDetail;

import java.util.List;

import com.rioc.ws.modeles.dao.BankDetail;
import com.rioc.ws.modeles.dto.BankDetailDto;

public interface IBankDetailService {
    BankDetail postBankDetail(BankDetailDto bankDetailDto);
    public List<BankDetailDto> getByPseudo(String pseudo);
    public BankDetailDto deleteByIban(String iban);
}
