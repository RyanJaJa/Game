package com.exce.repository;

import com.exce.model.BetWinningNumber;
import com.exce.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;


public interface BetWinNumRepository extends JpaRepository<BetWinningNumber, BigInteger> {

       BetWinningNumber findDetailsByGameIdOrderByIdDescCreateTimeDesc(BigInteger gameId) throws Exception;

       @Query("select bwn.game,bwn.raffleNumber,bwn.resultTime,bwn.result from BetWinningNumber bwn")
       List<BetWinningNumber> findAll();

       @Query("select bwn.game,bwn.raffleNumber,bwn.resultTime,bwn.result from BetWinningNumber bwn where bwn.game=?1")
       List<BetWinningNumber> findByGame(Game game);

       @Query("select bwn.game,bwn.raffleNumber,bwn.resultTime,bwn.result from BetWinningNumber bwn where bwn.createTime between ?1 and ?2")
       List<BetWinningNumber> findByCreateTimeBetween(Calendar calendarStart, Calendar calendarEnd);

       @Query("select bwn.game,bwn.raffleNumber,bwn.resultTime,bwn.result from BetWinningNumber bwn where bwn.game=?1 and bwn.createTime between ?2 and ?3")
       List<BetWinningNumber> findByGameAndCreateTime(Game game, Calendar calendarStart, Calendar calendarEnd);
}
