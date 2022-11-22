package com.rioc.ws.services.bankDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rioc.ws.exception.ApiException;
import com.rioc.ws.mapper.IAccountMapper;
import com.rioc.ws.modeles.dao.Account;
import com.rioc.ws.modeles.dao.BankDetail;
import com.rioc.ws.modeles.dto.BankDetailDto;
import com.rioc.ws.repositories.IBankDetailRepository;
import com.rioc.ws.services.account.IAccountService;

import nl.garvelink.iban.IBAN;
import nl.garvelink.iban.Modulo97;

@Service
public class BankDetailService implements IBankDetailService {

    private IBankDetailRepository repository;
    private IAccountService accountService;
    private IAccountMapper mapper;

    public BankDetailService(IBankDetailRepository repository, IAccountMapper mapper, IAccountService accountService) {
        super();
        this.repository = repository;
        this.mapper = mapper;
        this.accountService = accountService;
    }

    private boolean validateIBAN(BankDetailDto bankDetailDto) throws Exception {
        return Modulo97.verifyCheckDigits(bankDetailDto.getIban());
    }

    public BankDetail postBankDetail(BankDetailDto bankDetailDto) {
        Account account = accountService.getAccountByPseudo(bankDetailDto.getPseudo());
        if (bankDetailDto.getIban() != null
                && bankDetailDto.getIban().chars().filter(c -> c == (int) ' ').count() < 1) {
            try {
                if (validateIBAN(bankDetailDto)) {
                    IBAN iban = IBAN.valueOf(bankDetailDto.getIban());
                    bankDetailDto.setIban(iban.toString());
                    if (repository.findByIban(bankDetailDto.getIban()).isEmpty()) {
                        BankDetail bankDetail = mapper.bankDetailDtoToBankDetail(bankDetailDto);
                        bankDetail.setAccount(account);
                        return repository.save(bankDetail);
                    }
                }
            } catch (Exception e) {
                throw new ApiException("IBAN ERROR", HttpStatus.BAD_REQUEST);
            }
            throw new ApiException("IBAN FORMAT INCORRECT", HttpStatus.BAD_REQUEST);
        }
        throw new ApiException("IBAN FORMAT INCORRECT (WITHOUT SPACE)", HttpStatus.BAD_REQUEST);
    }

    public List<BankDetailDto> getByPseudo(String pseudo) {
        List<BankDetail> liste = repository.findAll();
        List<BankDetailDto> listeByPseudo = new ArrayList<>();
        for (BankDetail bd : liste) {
            if (bd.getAccount().getPseudo().equals(pseudo)) {
                listeByPseudo.add(mapper.bankDetailToBankDetailDto(bd));
            }
        }
        return listeByPseudo;
    }

    public BankDetailDto deleteByIban(String iban) {
        IBAN ib = IBAN.valueOf(iban);
        Optional<BankDetail> delete = repository.findByIban(ib.toString());
        if (delete.isEmpty()) {
            throw new ApiException("IBAN DOESN'T EXIST", HttpStatus.NOT_FOUND);
        }
        repository.delete(delete.get());
        return mapper.bankDetailToBankDetailDto(delete.get());
    }
}
