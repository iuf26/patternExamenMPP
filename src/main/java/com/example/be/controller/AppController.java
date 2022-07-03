package com.example.be.controller;

import com.example.be.domain.*;
import com.example.be.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class AppController {




        @Autowired
        private Service service;




    @GetMapping("/login")
    public ResponseEntity<CustomResponse> doLogin(@RequestParam(value = "alias") String playerAlias){
        try{
            System.out.println("In login with alias");
            GameConfig board = service.doLogIn(playerAlias);
            int initialPoints = Integer.parseInt(board.getValues().get(board.getValues().size() - 1));
            Game newGame = new Game(0, LocalDateTime.now(),playerAlias,"",initialPoints,0);
            service.addGame(newGame);//id-ul va fi setat de salvarea cu hibernate
            System.out.println(newGame.getId());
            System.out.println("Log in succes");


            System.out.println("showd send message");
            board.setCurrentGameId(newGame.getId());
            return ResponseEntity.status(HttpStatus.OK).body(
                    CustomResponse.builder().data(Map.of("board",board))
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()

            );
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    CustomResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()

            );

        }
    }

    @PostMapping("/game-config")
    public ResponseEntity<CustomResponse> addGameConfiguration(@RequestBody GameConfig gameConfig){

        StringBuilder gameConfigString = new StringBuilder();
        for (String elem : gameConfig.getValues()){
            //daca este deja string acestu nu mai trebuie convertit
            gameConfigString.append(elem).append("/");
        }
        gameConfigString.deleteCharAt(gameConfigString.length() - 1);
        service.addGameConfiguration(gameConfigString.toString());
        return ResponseEntity.status(HttpStatus.OK).body(
                CustomResponse.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }


    @GetMapping("/games/{id}")
    public ResponseEntity<CustomResponse> getGame(@PathVariable(name = "id") int id){
        try {
            Game game  = service.getRepoGame().findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    CustomResponse.builder()
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .data(Map.of("game",game))
                            .build());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    CustomResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message(ex.getMessage())
                            .build());
        }

    }



    @GetMapping("/score/{playerAlias}")
    public ResponseEntity<CustomResponse> getStatistics(@PathVariable("playerAlias") String player)
    {
        return     ResponseEntity.status(HttpStatus.OK).body(
                CustomResponse.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("statistics",service.getStatistics(player
                        )))
                        .build());
    }


    @PutMapping("/score")
    public ResponseEntity<CustomResponse> updateScore(@RequestBody PlayerDTO player){
        System.out.println("in update prize");

        try{
            service.updateScore(new Game(player.getCurrentGameId(),null,player.getPlayerAlias(),"",player.getPlayerScore(),0));
            return ResponseEntity.status(HttpStatus.OK).body(
                    CustomResponse.builder()
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()

            );



        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    CustomResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message(ex.getMessage())
                            .build()

            );
        }
    }

    @PutMapping("/letters")
    public ResponseEntity<CustomResponse> updateLetters(@RequestBody PlayerDTO player){
        System.out.println("in update letters");

        try{
            service.updateLetters(new Game(player.getCurrentGameId(),null,player.getPlayerAlias(),player.getPlayerLetters(),player.getPlayerScore(),0));
            return ResponseEntity.status(HttpStatus.OK).body(
                    CustomResponse.builder()
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()

            );



        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    CustomResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message(ex.getMessage())
                            .build()

            );
        }
    }



    @GetMapping("/scores")
    public ResponseEntity<CustomResponse> getGlobalScores(){
        System.out.println("Scores");
        return ResponseEntity.status(HttpStatus.OK).body(
                CustomResponse.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("scores",service.getGlobalScores()))
                        .build());
    }


    @PutMapping("/{gameId}/{status}")
    public ResponseEntity<CustomResponse> updateGameStatus(@PathVariable(name = "gameId") int gameId,@PathVariable("status") int status){
            try{
                System.out.println("in update game");
                service.updateGameStatus(gameId,status);

               return  ResponseEntity.status(HttpStatus.OK).body(
                        CustomResponse.builder()
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
            }catch (Exception ex){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        CustomResponse.builder()
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .build()

                );
            }
    }




    @GetMapping("/propose-letter/{available}")
    public ResponseEntity<CustomResponse> getServerProposedLetter(@PathVariable(name = "available") String letters){
        if(!Objects.equals(letters, "")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    CustomResponse.builder()
                            .status(HttpStatus.OK)
                            .data(Map.of("proposed",service.getRandomLetter(letters)))
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    CustomResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message("Invalid letters")
                            .build());
        }


    }




}
