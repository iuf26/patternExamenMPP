package com.example.be.repository;

import java.util.List;

public interface Repo<T,ID> {

    public List<T> findAll();
    /*id valid*/
    public  T findById(ID id) throws Exception;
    public  ID add(T elem);
    /*id valid*/
    public void update(T elem);
    /*id valid*/
    public  void delete(T elem);
}
