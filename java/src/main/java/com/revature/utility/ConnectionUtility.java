package com.revature.utility;

import org.postgresql.Driver;

import  java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtility {

    public static Connection getConnection() throws SQLException{
        String url = System.getenv("db_url");
        String username = System.getenv("db_username");
        String password = System.getenv("db_password");

        DriverManager.registerDriver(new Driver());

        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
        //grabbed this from the examples because I don't want to spend 3 hours re-figuring this out and I'm allowed too.
        //code just grabs my systems postgres passwords and establishes a connection with it, this should allow me to connect
        // to DBeaver. Driver manager grabs Gradle Driver,
    }
}
