package com.example.musiccatalog.plain.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface CoreDao {

    default Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
          "jdbc:postgresql://localhost:5432/music-db",
                "rrezahatam",
                "password"
        );
    }

    default void closeConnection(Connection connection) throws SQLException{
        if(connection == null) return;

        connection.close();
    }
}
