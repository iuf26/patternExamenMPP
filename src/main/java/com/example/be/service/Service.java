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
        return repoGame.getRandomGameConfig();
    }

    /**
     * se actualizeaza scorul jocului
     * @param game
     */
    public void updateGame(Game game){

        repoGame.updateGame(game);

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

}
