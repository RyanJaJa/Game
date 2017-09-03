package com.exce.repository;

import com.exce.model.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;

@Repository
public interface StoredValueRepository extends CrudRepository<TransactionHistory, BigInteger> {

    ArrayList<TransactionHistory> findDetailsByUserIdOrderByIdDescCreateTimeDesc(BigInteger userId, Pageable pageable) throws Exception;

    Page<TransactionHistory> findDetailsPageableByUserIdOrderByIdDescCreateTimeDesc(BigInteger userId, Pageable pageable) throws Exception;

    TransactionHistory save(TransactionHistory transactionDetail);

}
