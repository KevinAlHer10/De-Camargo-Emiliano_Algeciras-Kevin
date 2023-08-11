package com.backend.integrador.dao.model; //la capa de modelado no deberia estar dentro de la capa de persistencia

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dentist {
    private final static Logger log = Logger.getLogger(String.valueOf(DentistDaoH2.class));//el logger deberia consignar la clase presente

    private List<Dentist> dentists;

    private Integer countId = 0;

    public DentistDao(){
        this.dentists = new ArrayList<>();
    }

    @Override
    public Dentist save(Dentist dentist) {
        log.info("Registering the dentist: " + dentist.toString());
        countId++;
        dentist.setId(Long.valueOf(countId));
        dentists.add(dentist);
        log.info("Dentist registered successfully with id = " + dentist.getId());
        return dentist;
    }

    @Override
    public Dentist findById(Long id) {
        Dentist dentist = null;
        log.info("Searching dentist with id = " + id);
        for (Dentist dentist1: dentists) {
            if (dentist1.getId().equals(id)){
                dentist = dentist1;
            }
        }
        if (Objects.nonNull(dentist)){
            log.info("Dentist found: " + dentist.toString());
        }
        else {
            log.error("Error searching dentist with id = " + id);
        }
        return dentist;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting the dentist with id = " + id);
        for (Dentist dentist1: dentists) {
            if (dentist1.getId().equals(id)){
                dentists.remove(dentist1);
            }
        }
    }

    @Override
    public List<Dentist> findAll() {
        log.info("Searching all the dentists");
        log.info("Returning all the dentists: " + dentists.toString());
        return dentists;
    }

    @Override
    public Dentist update(Dentist dentist) {
        for (Dentist dentist1: dentists) {
            if (dentist1.getId().equals(dentist.getId())){
                dentist1.setName(dentist.getName());
                dentist1.setEnrollment(dentist.getEnrollment());
                dentist1.setSurname(dentist.getSurname());
            }
        }
        log.info("Dentist updated successfully with id = " + dentist.getId());
        return dentist;
    }
}
