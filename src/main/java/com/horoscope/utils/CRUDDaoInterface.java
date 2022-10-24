package com.horoscope.utils;

import java.util.List;

public interface CRUDDaoInterface<T> {

    //DAO - Data Access Object
    // a pattern that provides an abstract interface to
    // some type of database or other persistence mechanism

    int create(T t);

    List<T> getAll();

    T getByID(int id);

    T update(T t);

    boolean delete(T t);

    boolean deleteByID(int id);
}
