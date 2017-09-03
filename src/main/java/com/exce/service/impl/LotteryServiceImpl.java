package com.exce.service.impl;

import com.exce.exception.CustomError;
import com.exce.exception.GoldLuckException;
import com.exce.model.BetWinningNumber;
import com.exce.model.Game;
import com.exce.repository.BetWinNumRepository;
import com.exce.repository.GameRepository;
import com.exce.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    BetWinNumRepository betWinNumRepository;

    @Autowired
    GameRepository gameRepository;

    @Override
    public List<BetWinningNumber> getLotteryResult(BigInteger gameId,String createTime) throws Exception{

        Optional<Game> gameOptional = gameRepository.findGameById(gameId);

        if (!gameOptional.isPresent()) {
            throw new GoldLuckException(CustomError.NOT_FOUND_GAME.getErrorCode(),CustomError.NOT_FOUND_GAME.getErrorDesc());
        }

        List<BetWinningNumber> betWinNumList;

        if(gameId==null || gameId.equals(0) && createTime==null || "".equals(createTime)){
            betWinNumList= betWinNumRepository.findAll();
            return betWinNumList;
        }

        if(createTime==null || "".equals(createTime)) {
            betWinNumList = betWinNumRepository.findByGame(gameOptional.get());
            return betWinNumList;
        }

        String dateString = createTime + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date utilDate = sdf.parse(dateString);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(utilDate);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(utilDate);
        calendarEnd.add(Calendar.DAY_OF_MONTH,+1);

        if(gameId==null || gameId.equals(0)) {
            betWinNumList = betWinNumRepository.findByCreateTimeBetween(calendarStart, calendarEnd);
            return betWinNumList;
        }

        betWinNumList = betWinNumRepository.findByGameAndCreateTime(gameOptional.get(),calendarStart, calendarEnd);

        return betWinNumList;

    }

    @Override
    public BetWinningNumber updateLotteryResult(BigInteger winningId) throws Exception{
        return new BetWinningNumber();
    }

}
