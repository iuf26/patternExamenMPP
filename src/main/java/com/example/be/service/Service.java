package com.example.be.service;

import com.example.be.domain.Game;
import com.example.be.domain.GameConfig;
import com.example.be.domain.UserPack;
import com.example.be.repository.AbstractRepo;
import com.example.be.repository.RepoGame;
import com.example.be.repository.RepoPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.util.List;
import java.util.Random;


@org.springframework.stereotype.Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    @Autowired
    RepoGame repoGame;
    @Autowired
    AbstractRepo<String,String> repoPlayer;


    /**
     *
     * @param config
     */
    public void addGameConfiguration(String config){
        repoGame.addGameConfiguration(config);
    }


    /**
     *
     * @param player
     * @return statistica legata de jocurile la care a pariticipat un jucator
     */
    public UserPack getStatistics(String player) {
            return repoGame.getStatistics(player);
    }

    /**
     *
     * @param game
     * adauga un joc in baza de date,iar id-ul lui game se va schimba cu id-ul primit special
     * de la baza de date
     */
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
    public void updateScore(Game game) throws Exception {
        Game oldGame = repoGame.findById(game.getId());
        oldGame.setScore(game.getScore());
        repoGame.updateGame(oldGame);

    }



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

    public List<Game> getGlobalScores(){
        return repoGame.getGlobalScores();
    }


    public void updateGameStatus(int gameId,int gameStatus) throws Exception{

        Game oldGame = repoGame.findById(gameId);
        oldGame.setStatus(gameStatus);
            repoGame.updateGame(oldGame);
    }

    public String getRandomLetter(String letters) {
        Random random = new Random();
        int index = random.nextInt(letters.length());
        return String.valueOf(letters.charAt(index));
    }

}
