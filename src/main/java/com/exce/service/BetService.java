package com.exce.service;

import com.exce.dto.BetDetailRequest;
import com.exce.dto.BetDetailResponse;
import com.exce.dto.BetOrderRequest;
import com.exce.dto.BetOrderResponse;
import com.exce.model.BetOrder;
import com.exce.model.Game;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;

/**
 * 遊戲下注相關Action說明:
 * getBetDetail: 取得玩家投注詳細內容
 * singleBet: 投注單期下單
 * multipleBet: 投注追號下單
 * getGameList: 取得遊戲列表
 * getChaseBetDetail: 玩家追號查詢
 * stopChaseBet: 停止追號
 * getBetBalance: 盈虧記錄查詢
 */
public interface BetService {
    //BetDetailRequest betDetailRequest
    List<BetOrder> getBetDetail(BigInteger playerId,BigInteger gameId) throws Exception;
    BetOrder LotteryBet(BetOrder betOrder) throws Exception;
    List<Game> getGameList() throws Exception;
    List<Object> getChaseBetDetail(BigInteger userId, String createTime, String chaseStatus) throws Exception;
    BetOrderResponse stopChaseBet(BigInteger userId) throws Exception;
    BetOrderResponse getBetBalance(BigInteger userId) throws Exception;
}
