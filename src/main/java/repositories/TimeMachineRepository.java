package repositories;

import config.DatabaseConfig;
import models.TimeMachine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimeMachineRepository {
    private final DatabaseConfig config;

    public TimeMachineRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void add(String name, int capacity) {
        String sql = "INSERT INTO timemachines(name, capacity, isFree) values (?, ?, ?)";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, capacity);
            stmt.setBoolean(3, true);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TimeMachine getTimeMachineByName(String name) throws SQLException {
        String sql = "SELECT name, capacity, isFree FROM timemachines WHERE name = ?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            // CHECK IF THIS WORKS!!! IF YES!!!!! IMPLEMENT EVERY getByName!!!!!
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new TimeMachine(
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getBoolean("isFree")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(String name) throws SQLException {
        String sql = "DELETE FROM timemachines WHERE name =?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(String originalName, String newName) throws SQLException {
        TimeMachine timeMachine = getTimeMachineByName(originalName);

        String sql = "UPDATE timemachines SET name = ? WHERE name = ?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, timeMachine.getName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCapacity(String name, int newCapacity) throws SQLException {
        TimeMachine timeMachine = getTimeMachineByName(name);

        String sql = "UPDATE timemachines SET capacity = ? WHERE name = ?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newCapacity);
            stmt.setString(2, timeMachine.getName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStatus(String name, Boolean isFree) throws SQLException {
        TimeMachine timeMachine = getTimeMachineByName(name);

        String sql = "UPDATE timemachines SET isFree = ? WHERE name = ?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, isFree);
            stmt.setString(2, timeMachine.getName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TimeMachine> getAll() {
        String sql = "SELECT name, capacity, isFree FROM timemachines";
        List<TimeMachine> result = new ArrayList<>();

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()

        ) {
            while (rs.next()) {
                result.add(new TimeMachine(
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getBoolean("isFree")
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
