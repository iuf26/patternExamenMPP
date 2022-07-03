package com.example.be.service;

import com.example.be.domain.*;
import com.example.be.repository.AbstractRepo;
import com.example.be.repository.Repo;
import com.example.be.repository.RepoGame;
import com.example.be.repository.RepoPlayer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@org.springframework.stereotype.Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Service implements  IIService {

    @Autowired
    RepoGame repoGame;
    @Autowired
    RepoPlayer repoPlayer;


    /**
     *
     * @param config
     */
    @Override
    public void addGameConfiguration(String config){
        repoGame.addGameConfiguration(config);
    }


    /**
     *
     * @param player
     * @return statistica legata de jocurile la care a pariticipat un jucator
     */
    @Override
    public UserPack getStatistics(String player) {
            return repoGame.getStatistics(player);
    }

    /**
     *
     * @param game
     * adauga un joc in baza de date,iar id-ul lui game se va schimba cu id-ul primit special
     * de la baza de date
     */
    @Override
    public void  addGame(Game game){
            int givenID = repoGame.add(game);

    }




    /**
     *
     * @param alias
     * @return o configuratie aleatoare de joc daca exista un player cu alias -ul rspectiv
     *
     * @throws Exception
     */
    @Override
    public GameConfig doLogIn(String alias) throws Exception {
        repoPlayer.findById(alias);
        GameConfig gameConfig = repoGame.getRandomGameConfig();
        int i = 1;
        int starterScore = 0;
        for(String elem : gameConfig.getValues()){
            if(i % 2==0 ){
                starterScore += Integer.parseInt(elem);
            }
            i++;
        }
        List<String> initValues = gameConfig.getValues();
        initValues.add(String.valueOf(starterScore));
        gameConfig.setValues(initValues);



        return gameConfig;
    }

    /**
     * se actualizeaza scorul jocului
     * @param game
     */
    @Override
    public void updateScore(Game game) throws Exception {
        Game oldGame = repoGame.findById(game.getId());
        oldGame.setScore(game.getScore());
        repoGame.updateGame(oldGame);

    }



    @Override
    public void updateLetters(Game game) throws Exception {
        Game oldGame = repoGame.findById(game.getId());
        String oldLetters  =  oldGame.getLetters();
        String newLetters =  oldLetters + game.getLetters();

        oldGame.setLetters(newLetters);
        System.out.println(oldGame.getLetters());
        oldGame.setScore(game.getScore());
        repoGame.updateGame(oldGame);
    }

    /**
     *
     * @return clasamentul  global al jocului
     */

    @Override
    public List<Game> getGlobalScores(){
        return repoGame.getGlobalScores();
    }


    @Override
    public void updateGameStatus(int gameId, int gameStatus) throws Exception{

        Game oldGame = repoGame.findById(gameId);
        oldGame.setStatus(gameStatus);
            repoGame.updateGame(oldGame);
    }

    @Override
    public String getRandomLetter(String letters) {
        Random random = new Random();
        int index = random.nextInt(letters.length());
        return String.valueOf(letters.charAt(index));
    }


}
