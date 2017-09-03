package com.exce.repository;

import com.exce.model.BetOrderDetail;
import com.exce.model.BetOrder;
import org.springframework.data.jpa.repository.JpaRepository;


import java.math.BigInteger;
import java.util.ArrayList;

public interface BetRepository extends JpaRepository<BetOrderDetail, BigInteger> {

    ArrayList<BetOrderDetail> findByBetOrderOrderByCreateTimeDesc(BetOrder betOrder);

    //@Query("select o,d,g from BetOrder o,BetDetail d,Game g")
    ArrayList<BetOrderDetail> findAll();

}
