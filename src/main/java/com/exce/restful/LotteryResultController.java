package com.exce.restful;

import com.exce.exception.CustomError;
import com.exce.exception.GoldLuckException;
import com.exce.model.BetWinningNumber;
import com.exce.model.ResponsePayload;
import com.exce.service.LotteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


/**
 * 樂透開獎Action說明:
 * getLotteryResult: 取得遊戲開獎結果
 * updateLotteryResult: 新增/修改遊戲開獎結果
 */

@RestController
@RequestMapping(value = "lotteryResult")
public class LotteryResultController {
    private static Logger log = LoggerFactory.getLogger(LotteryResultController.class);

    @Autowired
    LotteryService lotteryService;
    /**
     * 取得遊戲開獎結果 <br>
     * request url: http://localhost:port/lotteryResult/game/{gameId}/times/{createTime}
     *
     * @return ResponsePayload<BetWinningNumber>
     * @RequestMethod Get
     */

    @GetMapping(value = "game/{gameId}/times/{createTime}")
    public @ResponseBody
    ResponsePayload<List<BetWinningNumber>> getLotteryResult(@PathVariable BigInteger gameId, @PathVariable String createTime) {
        log.info("Controller - getLotteryResult request ");
        ResponsePayload<List<BetWinningNumber>> payload = new ResponsePayload<>();

        try {
            payload.setData(lotteryService.getLotteryResult(gameId,createTime));
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - getLotteryResult error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - getLotteryResult error : {} ", e.getMessage());
        }
        log.info("Controller - getLotteryResult response : {} ", payload.toString());
        return payload;
    }

    /**
     * 新增/修改遊戲開獎結果 <br>
     * request url: http://localhost:port/lotteryResult
     *
     * @return ResponsePayload<BetWinningNumber>
     * @RequestMethod Put
     */
    @PutMapping(value = "winningId/{winningId}")
    public @ResponseBody
    ResponsePayload<BetWinningNumber> updateLotteryResult(@PathVariable BigInteger winningId) {
        log.info("Controller - updateLotteryResult request ");
        ResponsePayload<BetWinningNumber> payload = new ResponsePayload<>();

        try {
            //todo
            payload.setData(lotteryService.updateLotteryResult(winningId));
            payload.setErrorCode(CustomError.OK.getErrorCode());
            payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {
            payload.setErrorCode(e.getErrorCode());
            payload.setErrorDescription(e.getMessage());
            log.error("Controller - updateLotteryResult error : {} ", e.getMessage());
        } catch (Exception e) {
            payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            log.error("Controller - updateLotteryResult error : {} ", e.getMessage());
        }
        log.info("Controller - updateLotteryResult response : {} ", payload.toString());
        return payload;
    }
}
