package repositories;

import config.DatabaseConfig;
import models.TimePeriod;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimePeriodRepository {
    private final DatabaseConfig config;

    public TimePeriodRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void add(String name, String description) {
        String sql = "INSERT INTO timeperiods (name, description) values (?, ?)";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TimePeriod getTimePeriodByName(String name) throws SQLException {
        String sql = "Select name, description FROM timeperiods WHERE name =?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new TimePeriod(
                        rs.getString("name"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(String originalName, String newName) throws SQLException {
        TimePeriod timePeriod = getTimePeriodByName(originalName);

        String sql = "UPDATE timeperiods SET name = ? WHERE name = ?";
        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, timePeriod.getName());
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDescription(String name, String description) throws SQLException {
        TimePeriod timePeriod = getTimePeriodByName(name);

        String sql = "UPDATE timeperiods SET description = ? WHERE name = ?";
        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, description);
            stmt.setString(2, timePeriod.getName());
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(String name) throws SQLException {
        String sql = "DELETE FROM timeperiods WHERE name = ? ";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("No TimePeriod deleted. No row matched name = " + name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
