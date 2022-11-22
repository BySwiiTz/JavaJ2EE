package com.rioc.ws.services.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rioc.ws.exception.ApiException;
import com.rioc.ws.mapper.IAccountMapper;
import com.rioc.ws.modeles.dao.Account;
import com.rioc.ws.modeles.dto.AccountDto;
import com.rioc.ws.repositories.IAccountRepository;
import com.rioc.ws.services.adress.IAdressService;

@Service
public class AccountService implements IAccountService {

    private IAccountRepository repository;
    private IAccountMapper mapper;
    private IAdressService adrService;

    public AccountService(IAccountRepository repository, IAccountMapper mapper, IAdressService adrService) {
        super();
        this.repository = repository;
        this.mapper = mapper;
        this.adrService = adrService;
    }

    public Account postAccount(AccountDto accountDto) {
        if (!repository.findByPseudo(accountDto.getPseudo()).isEmpty()) {
            if (!repository.findByFirstNameAndLastName(accountDto.getFirstName(), accountDto.getLastName()).isEmpty()) {
                throw new ApiException("ACCOUNT ALREADY EXIST", HttpStatus.CONFLICT);
            }
            throw new ApiException("PSEUDO ALREADY EXIST", HttpStatus.CONFLICT);
        }
        accountDto.setAdress(adrService.validAdress(accountDto));
        Account account = mapper.accountDtoToAccount(accountDto);
        return repository.save(account);
    }

    public List<AccountDto> getAllAccount() {
        List<Account> liste = repository.findAll();
        List<AccountDto> listeDto = new ArrayList<>();
        for (Account ac : liste) {
            listeDto.add(mapper.accountToAccountDto(ac));
        }
        return listeDto;
    }

    public List<AccountDto> getByFirstName(String firstName) {
        List<Account> liste = repository.findAll();
        List<AccountDto> listeByName = new ArrayList<>();
        for (Account ac : liste) {
            if (ac.getFirstName().equals(firstName)) {
                listeByName.add(mapper.accountToAccountDto(ac));
            }
        }
        return listeByName;
    }

    public List<AccountDto> getByLastName(String lastName) {
        List<Account> liste = repository.findAll();
        List<AccountDto> listeByName = new ArrayList<>();
        for (Account ac : liste) {
            if (ac.getLastName().equals(lastName)) {
                listeByName.add(mapper.accountToAccountDto(ac));
            }
        }
        return listeByName;
    }

    public List<AccountDto> getByPseudo(String pseudo) {
        List<Account> liste = repository.findAll();
        List<AccountDto> listeByPseudo = new ArrayList<>();
        for (Account ac : liste) {
            if (ac.getPseudo().equals(pseudo)) {
                listeByPseudo.add(mapper.accountToAccountDto(ac));
            }
        }
        return listeByPseudo;
    }

    public Account getAccountByPseudo(String pseudo){
        if (repository.findByPseudo(pseudo).isEmpty()){
            throw new ApiException("ACCOUNT DOESN'T EXIST", HttpStatus.NOT_FOUND);
        }
        return repository.findByPseudo(pseudo).get(0);
    }

    public Account deleteAccount(String firstName, String lastName) {
        List<Account> liste = repository.findByFirstNameAndLastName(firstName, lastName);
        if (!liste.isEmpty() && liste != null) {
            repository.deleteAll(liste);
            return null;
        }
        throw new ApiException("ACCOUNT NO EXIST", HttpStatus.NOT_FOUND);
    }

    public Account deleteByPseudo(String pseudo) {
        List<Account> liste = repository.findByPseudo(pseudo);
        if (!liste.isEmpty() && liste != null) {
            repository.deleteAll(liste);
            return null;
        }
        throw new ApiException("ACCOUNT NO EXIST", HttpStatus.NOT_FOUND);
    }

    public String deleteAll() {
        List<Account> liste = repository.findAll();
        if (liste == null || liste.isEmpty()) {
            throw new ApiException("ACCOUNT LIST IS EMPTY", HttpStatus.NOT_FOUND);
        }
        repository.deleteAll();
        return "LIST CLEAN";
    }

}
