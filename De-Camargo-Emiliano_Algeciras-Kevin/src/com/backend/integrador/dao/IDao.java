package com.backend.integrador.dao;

public class IDao {
    package org.example.repository;

import java.sql.SQLException;
import java.util.List;

    public interface IDao<T> {
        T save(T t) throws SQLException;
        T findById(Long id);
        void delete(Long id);
        List<T> findAll();
        T update(T t);
    }
}
