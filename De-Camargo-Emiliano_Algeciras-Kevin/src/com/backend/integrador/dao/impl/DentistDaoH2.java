package com.backend.integrador.dao.impl;

import com.backend.integrador.dao.IDao;
import com.backend.integrador.dao.model.Dentist;
import com.backend.integrador.dataBase.SingletonH2;
import org.apache.log4j.Logger;

import java.sql.*;

public class DentistDaoH2 implements IDao<Dentist> {
    private final static Logger log = Logger.getLogger(String.valueOf(DentistDaoH2.class));

    SingletonH2 singletonH2 = SingletonH2.getInstance();
    private final static String INSERT_DENTIST_SQL = "INSERT INTO dentists(name,surname,enrollment) VALUES(?,?,?)";
    private final static String DELETE_DENTIST_BY_ID_SQL = "DELETE FROM dentists where id = ?";
    private final static String FIND_DENTIST_BY_ID_SQL = "SELECT id,name,surname,enrollment FROM dentists WHERE id = ?";
    private final static String FIND_ALL_DENTISTS_SQL = "SELECT * FROM dentists";
    private final static String UPDATE_DENTIST_SQL = "UPDATE dentists SET name = ?, surname = ?, enrollment = ? WHERE id = ?";

    @Override
    public Dentist save(Dentist dentist) throws SQLException {
        log.info("Registering the dentist: " + dentist.toString());
        try (Connection conn = singletonH2.open(); PreparedStatement statement = conn.prepareStatement(INSERT_DENTIST_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, dentist.getName());
            statement.setString(2, dentist.getSurname());
            statement.setInt(3, dentist.getEnrollment());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) dentist.setId(keys.getLong(1));

        } catch (SQLException throwables) {
            log.error("Error registering the dentist: " + dentist.toString(), throwables);
            throwables.printStackTrace();
        }
        log.info("Dentist registered successfully with id = " + dentist.getId());
        return dentist;
    }



    @Override
    public void delete(Long id) {
        log.info("Deleting the dentist with id = " + id);
        try (Connection connection = singletonH2.open(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DENTIST_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            log.error("Error deleting the dentist with id = " + id + " ==> ", throwables);
            throwables.printStackTrace();
        }
    }

    @Override
    public Dentist findById(Long id) {
        log.info("Searching dentist with id = " + id);
        Dentist dentist = null;

        try (Connection connection = singletonH2.open(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_DENTIST_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                Long patientId = result.getLong("id");
                String name = result.getString("name");
                String surname = result.getString("surname");
                int enrollment = result.getInt("enrollment");

                dentist = new Dentist(patientId, name, surname, enrollment);
            }

        } catch (SQLException throwables) {
            log.error("Error searching dentist with id = {}" + id + " ==> ", throwables);
            throwables.printStackTrace();
        }

        assert dentist != null;
        log.info("Dentist found: " + dentist.toString());
        return dentist;
    }

    @Override
    public List<Dentist> findAll() {
        log.info("Searching all the dentists");

        List<Dentist> dentists = new ArrayList<>();

        try (Connection connection = singletonH2.open(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DENTISTS_SQL); ResultSet result = preparedStatement.executeQuery()) {

            while (result.next()) {
                Long dentistID = result.getLong("id");
                String name = result.getString("name");
                String surname = result.getString("surname");
                int enrollment = result.getInt("enrollment");

                Dentist dentist = new Dentist(dentistID, name, surname, enrollment);
                dentists.add(dentist);
            }

        } catch (SQLException throwables) {
            log.error("Error searching all the dentists ==> ", throwables);
            throwables.printStackTrace();
        }
        log.info("Returning all the dentists: " + dentists.toString());
        return dentists;
    }

    @Override
    public Dentist update(Dentist dentist) {
        try (Connection connection = singletonH2.open(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DENTIST_SQL)) {

            preparedStatement.setString(1, dentist.getName());
            preparedStatement.setString(2, dentist.getSurname());
            preparedStatement.setInt(3, dentist.getEnrollment());
            preparedStatement.setLong(4, dentist.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            log.error("Error updating the dentist with id = " + dentist.getId(), throwables);
            throwables.printStackTrace();
        }
        log.info("Dentist updated successfully with id = " + dentist.getId());
        return dentist;
    }
}
