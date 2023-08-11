package com.backend.integrador.service;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.dao.model.Dentist;

import java.sql.SQLException;
import java.util.List;

public class DentistCacheService {
    private final IDao<Dentist> dentistDao;

    public DentistCacheService(IDao.IDao<Dentist> dentistDao) {
        this.dentistDao = dentistDao;
    }
    public Dentist register(Dentist dentist) throws SQLException {
        return dentistDao.save(dentist);
    }

    public void delete(Long id) throws SQLException {
        dentistDao.delete(id);
    }

    public Dentist findById(Long id) throws SQLException {
        return dentistDao.findById(id);
    }

    public List<Dentist> findAll() throws SQLException {
        return dentistDao.findAll();
    }

    public Dentist update(Dentist dentist) throws SQLException {
        return dentistDao.update(dentist);
    }
}
