package com.exce.service.impl;

import com.exce.dto.BetOrderResponse;
import com.exce.exception.CustomError;
import com.exce.exception.GoldLuckException;
import com.exce.model.*;
import com.exce.repository.*;
import com.exce.service.BetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Scope("prototype")
@Transactional
public class BetServiceImpl implements BetService{
    private static Logger log = LoggerFactory.getLogger(BetServiceImpl.class);

    @Autowired
    BetWinNumRepository betWinNumRepository;

    @Autowired
    BetRepository betDetailRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    BetOrderRepository betOrderRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<BetOrder> getBetDetail(BigInteger playerId, BigInteger gameId) throws Exception{


        Optional<User> userOptional = userRepository.findById(playerId);

        if (!userOptional.isPresent()) {
            throw new GoldLuckException(CustomError.NOT_FOUND_USER.getErrorCode(),CustomError.NOT_FOUND_USER.getErrorDesc());
        }

        Optional<Game> gameOptional = gameRepository.findGameById(gameId);

        if (!gameOptional.isPresent()) {
            throw new GoldLuckException(CustomError.NOT_FOUND_GAME.getErrorCode(),CustomError.NOT_FOUND_GAME.getErrorDesc());
        }

        ArrayList<BetOrder> orderList= betOrderRepository.findByPlayerAndGameOrderByCreateTimeDesc(userOptional.get(),gameOptional.get());

        return orderList;

    }

    @Override
    public BetOrder LotteryBet(BetOrder betOrder) throws Exception{

        Game game = gameRepository.findById(betOrder.getGame().getId());

        if (!gameRepository.exists(betOrder.getGame().getId())) {
            throw new GoldLuckException(CustomError.NOT_FOUND_GAME.getErrorCode(),CustomError.NOT_FOUND_GAME.getErrorDesc());
        }

        User user = userRepository.findUserById(betOrder.getPlayer().getId());

        if (!userRepository.exists(betOrder.getPlayer().getId())) {
            throw new GoldLuckException(CustomError.NOT_FOUND_USER.getErrorCode(),CustomError.NOT_FOUND_USER.getErrorDesc());
        }

        BetOrder order = new BetOrder();
        order.setGame(game);//gameid
        order.setPlayer(user);//playerid
        order.setBetTotalAmount(betOrder.getBetTotalAmount());//bet_total_amount
        order.setStatus(betOrder.getStatus());//status
        Calendar calendar = Calendar.getInstance();
        order.setCreateTime(calendar);

        //玩家有追號時
        if(betOrder.getChaseCount() != null || !"".equals(betOrder.getChaseCount())) {
            order.setChaseCount(betOrder.getChaseCount());
            order.setChaseStatus(betOrder.getChaseStatus());
        }

        betOrder.getBetOrderDetails().stream().forEach(list ->{
            BetOrderDetail orderDetail = new BetOrderDetail();
            orderDetail.setBetType(list.getBetType());
            orderDetail.setCreateTime(calendar);
            orderDetail.setBetItem(list.getBetItem());
            orderDetail.setRaffleNumber(list.getRaffleNumber());
            orderDetail.setBetValue(list.getBetValue());
            orderDetail.setOdds(list.getOdds());
            orderDetail.setBetOrder(order);
            order.getBetOrderDetails().add(orderDetail);
        });

        betOrderRepository.saveAndFlush(order);

        return betOrder;

    }


    @Override
    public List<Game> getGameList() throws Exception{
        List<Game> gameList;
        try {

            gameList = gameRepository.findAll();
            if (gameList == null || gameList.isEmpty()) {
                return null;
            }

        } catch (Exception e) {
            log.error("Service - getGameList error message : ", e);
            throw e;
        }
        return gameList;
    }


    @Override
    public List<Object> getChaseBetDetail(BigInteger userId,String createTime,String chaseStatus) throws Exception{

        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new GoldLuckException(CustomError.NOT_FOUND_USER.getErrorCode(),CustomError.NOT_FOUND_USER.getErrorDesc());
        }

        String dateString = createTime + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date utilDate = sdf.parse(dateString);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(utilDate);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(utilDate);
        calendarEnd.add(Calendar.DAY_OF_MONTH,+1);

        List<Object> betOrderList;

        //單筆下單查詢
        if(chaseStatus==null || "".equals(chaseStatus)) {
            betOrderList = betOrderRepository.findByPlayerAndCreateTimeBetween(userOptional.get(), calendarStart, calendarEnd);
            return betOrderList;
        }

        betOrderList = betOrderRepository.findByPlayerAndChaseStatusAndCreateTimeBetween(userOptional.get(),chaseStatus,calendarStart,calendarEnd);

        return betOrderList;
    }

    @Override
    public BetOrderResponse stopChaseBet(BigInteger userId) throws Exception{

        BetOrder order = new BetOrder();



        return new BetOrderResponse();
    }

    @Override
    public BetOrderResponse getBetBalance(BigInteger userId) throws Exception{
        return new BetOrderResponse();
    }

}
