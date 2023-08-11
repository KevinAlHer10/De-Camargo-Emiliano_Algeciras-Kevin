package com.backend.integrador.dataBase; //recuerden que los paquetes se nombran todo en minuscula

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonH2 {
    private final static Logger log = Logger.getLogger(String.valueOf(SingletonH2.class));
    private static final String DB_JDBC_DRIVER ="org.h2.Driver";

    private static final String DB_URL = "jdbc:h2:./Database/my;INIT=RUNSCRIPT FROM 'create.sql'";

    private final static String DB_USER = "root";

    private final static String DB_PASSWORD = "myPassword";

    private static SingletonH2 instance = null;

    private SingletonH2() {

    }

    public static SingletonH2 getInstance() {
        if(instance == null) instance = new SingletonH2();
        return instance;
    }

    public Connection open() {
        try {
            Class.forName(DB_JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }
    public void close(Connection connection) {
        try {
            if(connection != null) connection.close();
        } catch (SQLException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }
}
