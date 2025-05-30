package dao;

import connnection.DBConnectionUtil;
import entitiy.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    public Customer login(String username, String password) {
        String sql = """
                SELECT * FROM customer        
                WHERE username = ? AND password = ?
            """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Customer 객체 생성 (필드 순서/이름 맞게)
                    return new Customer(
                            rs.getLong("customer_id"), // 실제 컬럼명에 맞게!
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("license_number"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getDate("previous_rental_date") != null ? rs.getDate("previous_rental_date").toLocalDate() : null,
                            rs.getString("previous_car_type")
                    );
                } else {
                    return null; // 로그인 실패
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("DB 오류");
        }
    }

    public void save(Customer customer) {
        String sql = """
        INSERT INTO Customer (
            username, password, license_number, name,
            address, phone, email, previous_rental_date, previous_car_type
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getLicenseNumber());
            ps.setString(4, customer.getName());
            ps.setString(5, customer.getAddress());
            ps.setString(6, customer.getPhone());
            ps.setString(7, customer.getEmail());

            if (customer.getPreviousRentalDate() != null) {
                ps.setDate(8, java.sql.Date.valueOf(customer.getPreviousRentalDate()));
            } else {
                ps.setNull(8, java.sql.Types.DATE);
            }

            ps.setString(9, customer.getPreviousCarType());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("고객 저장 실패", e);
        }
    }

    public List<Customer> findAll() {
        String sql = "SELECT * FROM Customer";
        List<Customer> customers = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("license_number"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDate("previous_rental_date") != null ? rs.getDate("previous_rental_date").toLocalDate() : null,
                        rs.getString("previous_car_type")
                );
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("고객 조회 실패", e);
        }

        return customers;
    }

    public void update(Customer customer) {
        String sql = """
        UPDATE Customer SET
            username = ?, password = ?, license_number = ?, name = ?,
            address = ?, phone = ?, email = ?, previous_rental_date = ?, previous_car_type = ?
        WHERE id = ?
    """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getLicenseNumber());
            ps.setString(4, customer.getName());
            ps.setString(5, customer.getAddress());
            ps.setString(6, customer.getPhone());
            ps.setString(7, customer.getEmail());

            if (customer.getPreviousRentalDate() != null) {
                ps.setDate(8, java.sql.Date.valueOf(customer.getPreviousRentalDate()));
            } else {
                ps.setNull(8, java.sql.Types.DATE);
            }

            ps.setString(9, customer.getPreviousCarType());
            ps.setLong(10, customer.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("고객 수정 실패", e);
        }
    }

    public void updateBySql(String sql) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM Customer WHERE id = ?";

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("고객 삭제 실패", e);
        }
    }

    public void deleteBySql(String sql) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("고객 삭제 실패", e);
        }
    }
}
