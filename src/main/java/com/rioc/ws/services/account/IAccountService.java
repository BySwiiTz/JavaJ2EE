package com.rioc.ws.services.account;

import java.util.List;

import com.rioc.ws.modeles.dao.Account;
import com.rioc.ws.modeles.dto.AccountDto;

public interface IAccountService {
    public Account postAccount(AccountDto account);
    public List<AccountDto> getAllAccount();
    public List<AccountDto> getByFirstName(String firstName);
    public List<AccountDto> getByLastName(String lastName);
    public List<AccountDto> getByPseudo(String pseudo);
    Account getAccountByPseudo(String pseudo);
    public Account deleteAccount(String firstName, String lastName);
    public Account deleteByPseudo(String pseudo);
    public String deleteAll();
}
