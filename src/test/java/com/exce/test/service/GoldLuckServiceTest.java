package com.exce.test.service;

import com.exce.dto.Account;
import com.exce.dto.Balance;
import com.exce.exception.CustomError;
import com.exce.model.TransactionHistory;
import com.exce.model.User;
import com.exce.service.AuthService;
import com.exce.service.StoredValueService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class GoldLuckServiceTest {
    private static Logger log = LoggerFactory.getLogger(GoldLuckServiceTest.class);

    @Autowired
    StoredValueService storedValueService;
    @Autowired
    private AuthService authService;

    /**
     * 正向測試register
     */
    @Test
    public void stage_1_1_testRegisterSuccess() {
        try {
            log.info("Unit test service success - register");
            String username = "ryan";
            String password = "root";
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            user = authService.register(user);
            assertTrue(user.getId().compareTo(BigInteger.ZERO) > 0);
            log.info("Unit test service success - register response {}", user);
        } catch (Exception e) {
            log.error("UnitTest[testRegisterSuccess] error:\n", e);
        }
    }

    /**
     * 反向測試register
     */
    @Test
    public void stage_1_2_testRegisterFail() {
        try {
            log.info("Unit test service fail - register");
            String username = "ryan";
            String password = "root";
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user = authService.register(user);
            assertTrue(user == null);
            log.info("Unit test service fail - register response {}", user);
        } catch (Exception e) {
            assertTrue(CustomError.PLAYER_REGISTER_ERROR.getErrorDesc().equals(e.getMessage()));
            log.error("UnitTest[testRegisterSuccess] error:\n", e);
        }
    }

    /**
     * 正向測試deposit
     */
    @Test
    public void stage_1_3_testDepositSuccess() {
        try {
            log.info("Unit test service success - deposit");
            Account account = new Account();
            Balance balance = new Balance();
            BigDecimal transferAmount = BigDecimal.valueOf(10.1);
            balance.setTransferAmount(transferAmount);
            balance.setTransactionType((byte) 1);
            balance.setCurrencyCode("CNY");

            account.setUserId(BigInteger.valueOf(1L));
            account.setBalance(balance);
            TransactionHistory transactionDetail = storedValueService.deposit(account);

            assertTrue(transactionDetail.getDepositAmount().longValue() > 0);
            log.info("Unit test service success - deposit response {}", transactionDetail.toString());
        } catch (Exception e) {
            log.error("UnitTest[testDepositSuccess] error:\n", e);
        }
    }

    /**
     * 反向測試deposit
     */
    @Test
    public void stage_1_4_testDepositFail() {
        try {
            log.info("Unit test service fail - deposit");
            Account account = new Account();
            Balance balance = new Balance();
            balance.setTransferAmount(new BigDecimal(10.1));
            balance.setTransactionType((byte) 1);
            balance.setCurrencyCode("CNY");

            account.setUserId(BigInteger.ZERO);
            account.setBalance(balance);
            TransactionHistory transactionDetail = storedValueService.deposit(account);
            assertTrue(transactionDetail == null);
            log.info("Unit test service fail - deposit response {}", transactionDetail);
        } catch (Exception e) {
            assertTrue(CustomError.NOT_FOUND_USER.getErrorDesc().equals(e.getMessage()));
            log.error("UnitTest[testDepositFail] error:\n", e);
        }
    }

    /**
     * 正向測試withdraw
     */
    @Test
    public void stage_1_5_testWithdrawSuccess() {
        try {
            log.info("Unit test service success - withdraw");
            Account account = new Account();
            Balance balance = new Balance();
            balance.setTransferAmount(BigDecimal.valueOf(1.5));
            balance.setTransactionType((byte) 1);
            balance.setCurrencyCode("CNY");

            account.setUserId(BigInteger.valueOf(1L));
            account.setBalance(balance);

            TransactionHistory transactionDetail = storedValueService.withdraw(account);

            assertTrue(transactionDetail.getWithdrawAmount().longValue() > 0);
            log.info("Unit test service success - withdraw response {}", transactionDetail.toString());
        } catch (Exception e) {
            log.error("UnitTest[testWithdrawSuccess] error:\n", e);
        }
    }

    /**
     * 反向測試withdraw
     */
    @Test
    public void stage_1_6_testWithdrawFail() {
        try {
            log.info("Unit test service fail - withdraw");
            Account account = new Account();
            Balance balance = new Balance();
            balance.setTransferAmount(new BigDecimal(1.5));
            balance.setTransactionType((byte) 1);
            balance.setCurrencyCode("CNY");

            account.setUserId(BigInteger.ZERO);
            account.setBalance(balance);

            TransactionHistory transactionDetail = storedValueService.withdraw(account);
            assertTrue(transactionDetail == null);
            log.info("Unit test service fail - withdraw response {}", transactionDetail);
        } catch (Exception e) {
            assertTrue(CustomError.NOT_FOUND_USER.getErrorDesc().equals(e.getMessage()));
            log.error("UnitTest[testWithdrawFail] error:\n", e);
        }
    }

    /**
     * 正向測試getBalance
     */
    @Test
    public void stage_1_7_testGetBalanceSuccess() {
        try {
            log.info("Unit test service success - getBalance");
            BigInteger userId = BigInteger.valueOf(1L);
            Balance balance = storedValueService.getBalance(userId);

            assertTrue(balance.getTotal() != null);
            log.info("Unit test service success - getBalance response {}", balance.toString());
        } catch (Exception e) {
            log.error("UnitTest[testGetBalanceSuccess] error:\n", e);
        }
    }

    /**
     * 反向測試getBalance
     */
    @Test
    public void stage_1_8_testGetBalanceFail() {
        try {
            log.info("Unit test service fail - getBalance");
            BigInteger userId = BigInteger.ZERO;
            Balance balance = storedValueService.getBalance(userId);
            assertTrue(balance == null);
            log.info("Unit test service fail - getBalance response {}", balance);
        } catch (Exception e) {
            assertTrue(CustomError.NOT_FOUND_USER.getErrorDesc().equals(e.getMessage()));
            log.error("UnitTest[testGetBalanceFail] error:\n", e);
        }
    }

    /**
     * 正向測試finDetailsByUserName
     */
    @Test
    public void stage_1_9_testFindDetailsByUserIdSuccess() {
        try {
            log.info("Unit test service success - findDetailsByUserId");
            Pageable pageable = new PageRequest(0, 10);
            BigInteger userId = BigInteger.valueOf(1L);
            Page<TransactionHistory> transactionDetails = storedValueService.findDetailsByUserId(userId, pageable);

            assertTrue(transactionDetails.getNumberOfElements() > 0);
            log.info("Unit test service success - findDetailsByUserId response {}", transactionDetails.toString());
        } catch (Exception e) {
            log.error("UnitTest[testFindDetailsByUserIdSuccess] error:\n", e);
        }
    }

    /**
     * 反向測試finDetailsByUserName
     */
    @Test
    public void stage_2_1_testFindDetailsByUseIdFail() {
        try {
            log.info("Unit test service fail - findDetailsByUseId");
            Pageable pageable = new PageRequest(0, 10);
            BigInteger userId = BigInteger.ZERO;
            Page<TransactionHistory> transactionDetails = storedValueService.findDetailsByUserId(userId, pageable);
            assertTrue(transactionDetails == null);
            log.info("Unit test service fail - findDetailsByUseId response {}", transactionDetails);
        } catch (Exception e) {
            assertTrue(CustomError.NOT_FOUND_USER.getErrorDesc().equals(e.getMessage()));
            log.error("UnitTest[testFindDetailsByUseId] error:\n", e);
        }
    }

}
