package com.exce.repository;

import com.exce.model.BetOrder;
import com.exce.model.Game;
import com.exce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;

public interface BetOrderRepository extends JpaRepository<BetOrder, BigInteger>{

    ArrayList<BetOrder> findByPlayerAndGameOrderByCreateTimeDesc(User user, Game game);

    BetOrder findByPlayer(User user);

    ArrayList<BetOrder> findByPlayerAndGame(User user, Game game);

    ArrayList<BetOrder> findByplayer(User player);

    ArrayList<BetOrder> findByCreateTime(Calendar calendar);

    @Query(value="select o.createTime,o.game.name,o.chaseCount,o.chaseStatus,d.betItem,d.betType,d.raffleNumber from BetOrder o LEFT JOIN o.betOrderDetails d where o.player = ?1 and o.createTime between ?2 and ?3")
    ArrayList<Object> findByPlayerAndCreateTimeBetween(User player, Calendar calendarStart, Calendar calendarEnd);

    @Query(value="select o.createTime,o.game.name,o.chaseCount,o.chaseStatus,d.betItem,d.betType,d.raffleNumber from BetOrder o LEFT JOIN o.betOrderDetails d where o.player = ?1 and o.chaseStatus =?2 and o.createTime between ?3 and ?4")
    ArrayList<Object> findByPlayerAndChaseStatusAndCreateTimeBetween(User player,String chaseStatus, Calendar calendarStart, Calendar calendarEnd);

}
