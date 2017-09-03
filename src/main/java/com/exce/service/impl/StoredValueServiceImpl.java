package com.exce.service.impl;

import com.exce.dto.Account;
import com.exce.dto.Balance;
import com.exce.exception.CustomError;
import com.exce.exception.GoldLuckException;
import com.exce.model.TransactionHistory;
import com.exce.model.User;
import com.exce.repository.StoredValueRepository;
import com.exce.repository.UserRepository;
import com.exce.service.StoredValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class StoredValueServiceImpl implements StoredValueService {
    private static Logger log = LoggerFactory.getLogger(StoredValueServiceImpl.class);

    @Autowired
    StoredValueRepository storedValueRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Balance getBalance(BigInteger userId) throws Exception {
        Balance balance;
        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (!userOptional.isPresent()) {
                throw new GoldLuckException(CustomError.NOT_FOUND_USER.getErrorCode(),CustomError.NOT_FOUND_USER.getErrorDesc());
            }
            Pageable pageable = new PageRequest(0, 1);
            ArrayList<TransactionHistory> transactionDetails = storedValueRepository.findDetailsByUserIdOrderByIdDescCreateTimeDesc(userId, pageable);

            if (transactionDetails == null || transactionDetails.isEmpty()) {
                return null;
            }
            TransactionHistory transactionDetail = transactionDetails.get(0);

            balance = new Balance();
            balance.setUserId(transactionDetail.getUserId());
            balance.setCurrencyCode(transactionDetail.getCurrencyCode());
            balance.setTotal(transactionDetail.getBalanceAfter());
            balance.setTransactionType(transactionDetail.getTransactionType());

            log.info("Service - getBalance : {} ", balance);
        } catch (Exception e) {
            log.error("Service - getBalance error message : ", e);
            throw e;
        }
        return balance;
    }

    @Override
    public Page<TransactionHistory> findDetailsByUserId(BigInteger userId, Pageable pageable) throws Exception {
        Page<TransactionHistory> transactionDetails;

        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (!userOptional.isPresent()) {
                throw new GoldLuckException(CustomError.NOT_FOUND_USER.getErrorCode(),CustomError.NOT_FOUND_USER.getErrorDesc());
            }
            transactionDetails = storedValueRepository.findDetailsPageableByUserIdOrderByIdDescCreateTimeDesc(userId, pageable);

            if (transactionDetails == null || transactionDetails.getSize() == 0) {
                throw new GoldLuckException(CustomError.NOT_FOUND_TRANSACTION_DETAILS.getErrorCode(),CustomError.NOT_FOUND_TRANSACTION_DETAILS.getErrorDesc());
            }

            log.info("Service - finDetailsByUserName list size : {} ", transactionDetails.getNumberOfElements());
        } catch (Exception e) {
            log.error("Service - finDetailsByUserName error message : {} ", e.getMessage());
            throw e;
        }
        return transactionDetails;
    }

    @Override
    public TransactionHistory withdraw(Account account) throws Exception {
        TransactionHistory transactionDetail;
        try {
            BigInteger userId = account.getUserId();
            Balance balance = getBalance(userId);
            if (balance == null) {
                throw new GoldLuckException(CustomError.NOT_FOUND_BALANCE.getErrorCode(),CustomError.NOT_FOUND_BALANCE.getErrorDesc());
            }

            BigDecimal transferAmount = account.getBalance().getTransferAmount();
            BigDecimal total = balance.getTotal();
            //if transferAmount is negtive
            if (transferAmount.compareTo(total) > 0) {
                throw new GoldLuckException(CustomError.NOT_ENOUGH_FUNDS.getErrorCode(),CustomError.NOT_ENOUGH_FUNDS.getErrorDesc());
            } else {
                transactionDetail = new TransactionHistory();
                transactionDetail.setWithdrawAmount(transferAmount);
                transactionDetail.setBalanceBefore(total);
                total = total.subtract(transferAmount);
                transactionDetail.setBalanceAfter(total);
                transactionDetail.setUserId(balance.getUserId());
                transactionDetail.setCurrencyCode(account.getBalance().getCurrencyCode());
                transactionDetail.setTransactionType(account.getBalance().getTransactionType());
                transactionDetail = storedValueRepository.save(transactionDetail);
            }

            log.info("Service - withdraw detail : {} ", transactionDetail);
        } catch (Exception e) {
            log.error("Service - withdraw error message : ", e);
            throw e;
        }

        return transactionDetail;
    }

    @Override
    public TransactionHistory deposit(Account account) throws Exception {
        TransactionHistory transactionDetail;
        try {
            BigInteger userId = account.getUserId();
            Balance balance = getBalance(userId);

            //if can't find balance , create new transaction detail
            if (balance == null) {
                Optional<User> userOptional = userRepository.findById(userId);
                if (!userOptional.isPresent()) {
                    throw new GoldLuckException(CustomError.NOT_FOUND_USER.getErrorCode(),CustomError.NOT_FOUND_USER.getErrorDesc());
                }
                balance = new Balance();
                balance.setUserId(userOptional.get().getId());
                balance.setTotal(BigDecimal.ZERO);
            }

            BigDecimal transferAmount = account.getBalance().getTransferAmount();
            BigDecimal total = balance.getTotal();
            transactionDetail = new TransactionHistory();
            transactionDetail.setDepositAmount(transferAmount);
            transactionDetail.setBalanceBefore(total);
            total = total.add(transferAmount);
            transactionDetail.setBalanceAfter(total);
            transactionDetail.setUserId(balance.getUserId());
            transactionDetail.setCurrencyCode(account.getBalance().getCurrencyCode());
            transactionDetail.setTransactionType(account.getBalance().getTransactionType());
            transactionDetail = storedValueRepository.save(transactionDetail);

            log.info("Service - deposit detail : {} ", transactionDetail);
        } catch (Exception e) {
            log.error("Service - deposit error message : ", e);
            throw e;
        }

        return transactionDetail;
    }
}
