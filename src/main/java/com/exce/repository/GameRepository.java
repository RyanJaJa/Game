package com.exce.repository;

import com.exce.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, BigInteger> {

    //List<Game> findAll();
    Game findById(BigInteger id);
    Optional<Game> findGameById(BigInteger userId);
}
