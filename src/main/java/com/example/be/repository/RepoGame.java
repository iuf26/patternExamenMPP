package com.example.be.repository;

import com.example.be.domain.Game;
import com.example.be.domain.GameConfig;
import com.example.be.domain.UserPack;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RepoGame extends AbstractRepo<Game,Integer>{


    @Override
    public Integer add(Game game) {
        System.out.println("In add game with hibernate");
        Session sessionFirst = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = sessionFirst.getSessionFactory();

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(game);//seteaza id-ul acestui game

                tx.commit();



            }catch (RuntimeException ex) {
                System.err.println("Eroare  "+ex);
                if (tx != null)
                    tx.rollback();
            }


        }
            return game.getId();
    }

    public void addGameConfiguration(String config) {
        System.out.println("In add game configuration");
        try(PreparedStatement ps = connection.getConnection().prepareStatement("INSERT INTO game_config(config) values (?);")){

            ps.setString(1,config);
            ps.executeUpdate();


        }catch (SQLException e){
            System.err.println(e.getMessage());
        }


    }

    /*Nu o prea mai folosesc pe asta*/
    public UserPack getStatistics(String player){
        List<Game> all = new ArrayList<>();
        try(PreparedStatement ps = connection.getConnection().prepareStatement("SELECT * FROM games WHERE player=?;")){

            ps.setString(1,player);
            ps.executeQuery();
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    LocalDateTime beginTime  = rs.getTimestamp("begin_time").toLocalDateTime();
                    String playerAlias = rs.getString("player");
                    String letters = rs.getString("letters");
                    int id = rs.getInt("id");
                    int score  = rs.getInt("score");
                    int status = rs.getInt("status");
                    all.add(new Game(id,beginTime,playerAlias,letters,score,status));

                }

            }


        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return new UserPack(all);
    }

    public GameConfig getRandomGameConfig() throws Exception {

        try(PreparedStatement ps = connection.getConnection().prepareStatement("SELECT *\n" +
                "                FROM game_config\n" +
                "        ORDER BY random()\n" +
                "        LIMIT 1;")){

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    String gameConfig = rs.getString("config");

                    List<String> result = Arrays.stream(gameConfig.split("/")).collect(Collectors.toList());
                    return new GameConfig(result,0);
                }

            }


        }catch (SQLException e){
            throw new Exception(e.getMessage());
        }
        throw new Exception("Error generating game set");
    }

    public void updateGame(Game game){
        System.out.println("Update game");
        Session sessionFirst = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = sessionFirst.getSessionFactory();
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Game oldGame= session.load(Game.class,game.getId());
               oldGame.setLetters(game.getLetters());
               oldGame.setScore(game.getScore());
                oldGame.setStatus(game.getStatus());
                tx.commit();
            } catch(RuntimeException ex) {
                System.err.println("Error update + " + ex);
                if (tx!=null)
                    tx.rollback();

            }
        }
    }

    public List<Game> getGlobalScores() {
        System.out.println("Get global scores");
        List<Game> all = new ArrayList<>();


        Session sessionFirst = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = sessionFirst.getSessionFactory();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                all = session.createQuery("from Game g  where g.status in (:stat) order by g.score DESC",Game.class)
                        .setParameter("stat",1)
                        .list();
                tx.commit();
            }catch (RuntimeException ex) {
                System.err.println("Eroare la select all games"+ex);
                if (tx != null)
                    tx.rollback();
            }


        }
        return all;
    }

    @Override
    public Game findById(Integer gameId) throws Exception {
        System.out.println("In find by id");
        Session sessionFirst = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = sessionFirst.getSessionFactory();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Game game = session.createQuery("from Game where id  = :gid", Game.class)
                        .setParameter("gid",gameId)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return game;
            }catch (RuntimeException ex) {
                System.err.println("Eroare findById repoGame"+ex);

                if (tx != null)
                    tx.rollback();
                throw  new Exception("Invalid id");
            }


        }

    }
}
