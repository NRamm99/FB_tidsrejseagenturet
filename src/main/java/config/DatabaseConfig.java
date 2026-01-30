package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private final String url;
    private final String user;
    private final String password;

    public DatabaseConfig() {
        Properties props = new Properties();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException(
                        "Mangler db.properties i src/main/resources. " +
                                "Kopiér db.properties.example -> db.properties og udfyld."
                );
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Kunne ikke læse db.properties", e);
        }

        this.url = require(props, "db.url");
        this.user = require(props, "db.user");
        this.password = require(props, "db.password");
    }

    private String require(Properties p, String key) {
        String v = p.getProperty(key);
        if (v == null || v.isBlank()) {
            throw new IllegalStateException("db.properties mangler: " + key);
        }
        return v.trim();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void dbInit() {
        final String dbName = "tidsrejse_agenturet_db";

        // Connect to "server level" (no DB needed) by taking everything before the first "/" after host:port.
        // Example: jdbc:mysql://localhost:3306/tidsrejse_agenturet_db?useSSL=false
        // becomes:  jdbc:mysql://localhost:3306/?useSSL=false
        String serverUrl = url.replaceFirst("(?i)(jdbc:mysql://[^/]+)(/[^?]*)?(\\?.*)?$", "$1/$3");

        try (Connection conn = DriverManager.getConnection(serverUrl, user, password);
             var stmt = conn.createStatement()) {

            // 1) DB
            stmt.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS " + dbName +
                            " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci"
            );

            // 2) Select DB
            stmt.execute("USE " + dbName);

            // 3) Tables
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS customers (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(20) NOT NULL,
                        email VARCHAR(30) NOT NULL
                    )
                    """);

            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS guides (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(20) NOT NULL,
                        speciality VARCHAR(30) NOT NULL
                    )
                    """);

            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS timemachines (
                        name VARCHAR(20) NOT NULL PRIMARY KEY,
                        capacity INT NOT NULL,
                        isFree BOOLEAN NOT NULL
                    )
                    """);

            // NOTE: your original timeperiods had no PK. Keeping it EXACTLY as you wrote:
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS timeperiods (
                        name VARCHAR(20) NOT NULL,
                        description VARCHAR(200) NOT NULL
                    )
                    """);

            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS bookings (
                        customerName VARCHAR(100),
                        timeMachineName VARCHAR(100),
                        timePeriodName VARCHAR(100),
                        guideName VARCHAR(100)
                    )
                    """);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("dbInit failed: " + e.getMessage(), e);
        }
    }

}
