package repositories;

import config.DatabaseConfig;

import java.sql.SQLException;

public class BookingRepository {
    private final DatabaseConfig config;

    public BookingRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void add(String customerName, String timeMachineName, String timePeriodName, String guideName) {
        String sql = "INSERT INTO bookings (customerName, timeMachineName, timePeriodName, guideName) values (?, ?, ?, ?)";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerName);
            stmt.setString(2, timeMachineName);
            stmt.setString(3, timePeriodName);
            stmt.setString(4, guideName);


            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countByTimeMachineName(String timeMachineName) {
        String sql = "SELECT COUNT(*) AS c FROM bookings WHERE timeMachineName = ?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, timeMachineName);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("c");
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
