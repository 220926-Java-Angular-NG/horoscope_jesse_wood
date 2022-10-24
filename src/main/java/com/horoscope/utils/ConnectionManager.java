package com.horoscope.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
    private static Connection conn;
    private static Properties props;

    private ConnectionManager() {}

    public static Connection getConnection() throws SQLException {
        if (props == null) {
            props = loadProperties();
        }
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password")
            );
        }

        return conn;
    }
    private static Properties loadProperties() {
        Properties props = new Properties();

        try {
            FileInputStream inStrm = new FileInputStream("src/main/resources/horoscope.properties");
            props.load(inStrm);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return props;
    }
}
