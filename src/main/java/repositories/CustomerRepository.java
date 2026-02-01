package repositories;

import config.DatabaseConfig;
import models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final DatabaseConfig config;

    public CustomerRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void add(String name, String email) throws SQLException {
        String sql = "INSERT INTO customers(name, email) values (?, ?)";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer getCustomerByName(String name) throws SQLException {
        String sql = "SELECT id, name, email  FROM customers WHERE name =?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void remove(String name) throws SQLException {
        String sql = "DELETE FROM customers WHERE name =?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(String originalName, String newName) throws SQLException {
        Customer customer = getCustomerByName(originalName);

        String sql = "UPDATE customers SET name = ? WHERE id = ?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, customer.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCustomerEmail(String name, String newEmail) throws SQLException {
        Customer customer = getCustomerByName(name);

        String sql = "UPDATE customers SET email = ? WHERE id = ?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, customer.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getAll() {
        String sql = "SELECT id, name, email FROM customers";
        List<Customer> result = new ArrayList<>();

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                result.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
