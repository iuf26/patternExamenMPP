package com.example.be.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


@AllArgsConstructor
@Getter
@Setter
@Component
public class DbConnection {

    private Connection connection;


    public  DbConnection() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        String url = properties.getProperty("spring.datasource.url");
        String user = properties.getProperty("spring.datasource.username");
        String pass = properties.getProperty("spring.datasource.password");
        try {
            connection = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            connection = null;
        }

    }
}
