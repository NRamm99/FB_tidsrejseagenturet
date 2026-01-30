package repositories;

import config.DatabaseConfig;
import models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository {
    private final DatabaseConfig config;

    public CustomerRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void add(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers(name, email) values (?, ?)";

        try (var conn = config.getConnection();
        var stmt = conn.prepareStatement(sql)){

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer getCustomerByName(String name) throws SQLException{
        String sql = "SELECT id, name, email  FROM customers WHERE name =?";

        try (var conn = config.getConnection();
             var stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();
            return new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void remove(Customer customer){

    }
}
