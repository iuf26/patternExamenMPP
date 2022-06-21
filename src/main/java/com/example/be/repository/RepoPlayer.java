package com.example.be.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@AllArgsConstructor


public class RepoPlayer extends AbstractRepo<String,String> {

    @Override
    public String findById(String alias) throws Exception {
        try(PreparedStatement ps = connection.getConnection().prepareStatement("SELECT id FROM players WHERE alias =  ?;")){
            ps.setString(1,alias);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    throw  new Exception("Invalid alias");
                }
            }

        }catch (SQLException e){

            System.err.println(e);
        }
        return alias;
    }
}
