package edu.lab4.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Db {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Db.class.getResourceAsStream("/db.properties")) {
            if (in == null) {
                throw new IOException("Файл db.properties не знайдено");
            }
            PROPS.load(in);
            Class.forName("org.postgresql.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private Db() {
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(
                PROPS.getProperty("url"), PROPS.getProperty("user"), PROPS.getProperty("password"));
    }
}
