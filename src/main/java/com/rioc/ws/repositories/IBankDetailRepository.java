package com.rioc.ws.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rioc.ws.modeles.dao.BankDetail;

@Repository
public interface IBankDetailRepository extends JpaRepository<BankDetail, Integer>{
    public BankDetail findByIban(@Param(value = "iban") String iban);
}
