package repositories;

import config.DatabaseConfig;
import models.Customer;
import models.Guide;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuideRepository {
    private final DatabaseConfig config;

    public GuideRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void add(String name, String speciality) throws SQLException {
        String sql = "INSERT INTO guides(name, speciality) values (?, ?)";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, speciality);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Guide getGuideByName(String name) throws SQLException {
        String sql = "Select id, name, speciality FROM guides WHERE name =?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Guide(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("speciality")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(String originalName, String newName) throws SQLException {
        Guide guide = getGuideByName(originalName);

        String sql = "UPDATE guides SET name = ? WHERE id = ?";
        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, guide.getId());
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSpeciality(String name, String newSpeciality) throws SQLException {
        Guide guide = getGuideByName(name);

        String sql = "UPDATE guides SET speciality = ? WHERE id = ?";
        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newSpeciality);
            stmt.setInt(2, guide.getId());
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(String name) throws SQLException {
        String sql = "DELETE FROM guides WHERE name = ?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("No guide deleted. No row matched name = " + name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
