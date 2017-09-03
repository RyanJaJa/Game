package com.exce.service;

import com.exce.dto.Account;
import com.exce.dto.Balance;
import com.exce.model.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;

public interface StoredValueService {

    Balance getBalance(BigInteger userId) throws Exception;

    Page<TransactionHistory> findDetailsByUserId(BigInteger userId, Pageable pageable) throws Exception;

    TransactionHistory withdraw(Account account) throws Exception;

    TransactionHistory deposit(Account account) throws Exception;
}
