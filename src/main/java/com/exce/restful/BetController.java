package com.exce.restful;

import com.exce.dto.BetDetailRequest;
import com.exce.dto.BetDetailResponse;
import com.exce.dto.BetOrderRequest;
import com.exce.dto.BetOrderResponse;
import com.exce.exception.CustomError;
import com.exce.exception.GoldLuckException;
import com.exce.model.BetOrder;
import com.exce.model.Game;
import com.exce.model.ResponsePayload;
import com.exce.service.BetService;
import com.exce.validation.BetRequestValidator;
import com.exce.validation.StoredValueRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

/**
 * 遊戲下注相關Action說明:
 * getBetDetail: 取得玩家投注詳細內容
 * lotteryBet: 投注單期與追號下單
 * getGameList: 取得遊戲列表
 * getChaseBetDetail: 玩家追號查詢
 *
 * stopChaseBet: 停止追號
 * getBetBalance: 盈虧記錄查詢
 */

@RestController
@RequestMapping(value = "bet")
public class BetController {
    private static Logger log = LoggerFactory.getLogger(BetController.class);

    @Autowired
    BetService betService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new BetRequestValidator());
    }

    /**
     * 取得玩家投注詳細內容 <br>
     * request url: http://localhost:port/bet/detail/user/{userId}/game/{gameId}
     * @param playerId,gameId
     * @return ResponsePayload<BetOrderResponse>
     * @RequestMethod GET
     */
    @RequestMapping(value = "detail/user/{playerId}/game/{gameId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponsePayload<List<BetOrder>> getBetDetail(@PathVariable BigInteger playerId, @PathVariable BigInteger gameId) {
        log.info("Controller - getBetDetail request : {} , {} ", playerId , gameId);
        ResponsePayload<List<BetOrder>> payload = new ResponsePayload<>();

        try {
            payload.setData(betService.getBetDetail(playerId,gameId));
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - getBetDetail error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - getBetDetail error : {} ", e.getMessage());
        }
        log.info("Controller - getBetDetail response : {} ", payload.toString());
        return payload;
    }

    /**
     * 投注單期與追號下單 <br>
     * request url: http://localhost:port/bet/single
     *
     * @param betOrder
     * @return ResponsePayload<BetOrderResponse>
     * @RequestMethod Put
     *
     * Example：
     * {
     *       "game":{"id":1},
     *       "player":{"id":1},
     *       "betTotalAmount":1000.0,
     *       "status":"noneOpen",
     *       "betOrderDetails" : [
     *                 {
     *                      "betItem": "FOURTH",
     *                      "betType": "B",
     *                      "raffleNumber":4,
     *                      "betValue":500,
     *                      "odds":47.96
     *               },
     *               {
     *                      "betItem": "FOURTH",
     *                      "betType": "odd",
     *                      "raffleNumber":4,
     *                      "betValue":500,
     *                      "odds":47.96
     *              },
     *              {
     *                      "betItem": "TENTH",
     *                      "betType": "small",
     *                      "raffleNumber":5,
     *                      "betValue":500,
     *                      "odds":47.96
     *              }
     *      ],
     *      "chaseCount":2,
     *      "chaseStatus":"active"
     * }
     *
     */

    @RequestMapping(value = "lottery", method = RequestMethod.PUT)
    public @ResponseBody
    ResponsePayload<BetOrder> LotteryBet(@Valid @RequestBody BetOrder betOrder) {
        log.info("Controller - singleBet request : {} ", betOrder);
        ResponsePayload<BetOrder> payload = new ResponsePayload<>();

        try {
            payload.setData(betService.LotteryBet(betOrder));
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - singleBet error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - singleBet error : {} ", e.getMessage());
        }
        log.info("Controller - singleBet response : {} ", payload.toString());
        return payload;
    }

    /**
     * 取得遊戲列表 <br>
     * request url: http://localhost:port/bet/gameList
     *
     * @return ResponsePayload<ArrayList<Game>>
     * @RequestMethod Get
     */

    @RequestMapping(value = "gameList", method = RequestMethod.GET)
    public @ResponseBody
    ResponsePayload<List<Game>> getGameList() {
        log.info("Controller - getGameList request ");
        ResponsePayload<List<Game>> payload = new ResponsePayload<>();

        try {
            payload.setData(betService.getGameList());
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - getGameList error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - getGameList error : {} ", e.getMessage());
        }
        log.info("Controller - getGameList response : {} ", payload.toString());
        return payload;
    }

    /**
     * 玩家追號查詢 <br>
     * request url: http://localhost:port/bet/chaseDetail/user/{userId}/times/{times}/status/{status}
     *
     * @param userId
     * @return ResponsePayload<BetOrderResponse>
     * @RequestMethod Get
     * Example:
     *
     * http://localhost:2052/bet/chaseDetail/1/times/2017-08-16/status/done
     * {
     *      "data": [
     *           [
     *                  1502908630000,
     *                  "北京PK10",
     *                  null,
     *                  "active",
     *                  "第三名",
     *                  "5",
     *                  3
     *           ]
     *       ],
     *      "errorCode": "99SYS00000",
     *      "errorDescription": "Completed successfully"
     *}
     */

    @RequestMapping(value = "chaseDetail/user/{userId}/times/{createTime}/status/{chaseStatus}", method = RequestMethod.GET)
    public @ResponseBody
    ResponsePayload<List<Object>> getChaseBetDetail(@PathVariable BigInteger userId, @PathVariable String createTime, @PathVariable String chaseStatus) {

        log.info("Controller - getChaseBetDetail request : {} ", userId);
        ResponsePayload<List<Object>> payload = new ResponsePayload<>();

        try {
            payload.setData(betService.getChaseBetDetail(userId,createTime,chaseStatus));
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - getChaseBetDetail error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - getChaseBetDetail error : {} ", e.getMessage());
        }
        log.info("Controller - getChaseBetDetail response : {} ", payload.toString());
        return payload;
    }

    /**
     * 停止追號(部分欄位更新使用PATH方法) <br>
     * request url: http://localhost:port/bet/stopChase/{userId}
     *
     * @param userId
     * @return ResponsePayload<BetOrderResponse>
     * @RequestMethod Patch
     */

    @RequestMapping(value = "stopChase/{userId}", method = RequestMethod.PATCH)
    public @ResponseBody
    ResponsePayload<BetOrderResponse> stopChaseBet(@PathVariable BigInteger userId) {
        log.info("Controller - stopChaseBet request : {} ", userId);
        ResponsePayload<BetOrderResponse> payload = new ResponsePayload<>();

        try {
            //todo
            //payload.setData(betService.stopChaseBet(userId));
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - stopChaseBet error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - stopChaseBet error : {} ", e.getMessage());
        }
        log.info("Controller - stopChaseBet response : {} ", payload.toString());
        return payload;
    }

    /**
     * 盈虧記錄查詢 <br>
     * request url: http://localhost:port/betBalance/{userId}
     *
     * @param userId
     * @return ResponsePayload<BetOrderResponse>
     * @RequestMethod Get
     */
    @RequestMapping(value = "betBalance/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponsePayload<BetOrderResponse> getBetBalance(@PathVariable BigInteger userId) {
        log.info("Controller - getBetBalance request : {} ", userId);
        ResponsePayload<BetOrderResponse> payload = new ResponsePayload<>();

        try {
            //todo
            //payload.setData(betService.getBetBalance(userId));
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - getBetBalance error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - getBetBalance error : {} ", e.getMessage());
        }
        log.info("Controller - getBetBalance response : {} ", payload.toString());
        return payload;
    }
}
