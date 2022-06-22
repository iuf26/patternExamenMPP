package com.example.be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table( name = "games" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "begin_time")
    private LocalDateTime beginTime;
    @Column(name = "player")
    private String playerAlias;
    @Column(name="letters")
    private String letters;
    @Column(name = "score")
    private int score;
    @Column(name = "status")
    private int status;

}
