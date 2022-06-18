package com.example.be.repository;






import com.example.be.domain.DbConnection;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractRepo<T,ID> {


    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    DbConnection connection;

    public  List<T> findAll(){return null;}
    /*id valid*/
    public  T findById(ID id){return null;}
    public  ID add(T elem){return null;}
    /*id valid*/
    public void update(T elem){};
    /*id valid*/
    public  void delete(T elem){};
}
