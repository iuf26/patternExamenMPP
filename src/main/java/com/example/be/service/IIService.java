package com.example.be.service;

import com.example.be.domain.Game;
import com.example.be.domain.GameConfig;
import com.example.be.domain.UserPack;

import java.util.List;

public interface IIService {
    void addGameConfiguration(String config);

    UserPack getStatistics(String player);

    void addGame(Game game);

    GameConfig doLogIn(String alias) throws Exception;

    void updateScore(Game game) throws Exception;

    void updateLetters(Game game) throws Exception;

    List<Game> getGlobalScores();

    void updateGameStatus(int gameId, int gameStatus) throws Exception;

    String getRandomLetter(String letters);
}
