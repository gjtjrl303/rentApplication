package dao;

import connnection.DBConnectionUtil;
import entitiy.Rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RentalDao {

    public void save(Rental rental) {
        String sql = """
        INSERT INTO Rental (
            car_id, customer_id, company_id,
            license_number, rental_start_date, rental_duration_days,
            total_fee, payment_due_date, additional_items
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, rental.getCarId());
            ps.setLong(2, rental.getCustomerId());
            ps.setLong(3, rental.getCompanyId());
            ps.setString(4, rental.getLicenseNumber());

            if (rental.getRentalStartDate() != null)
                ps.setDate(5, java.sql.Date.valueOf(rental.getRentalStartDate()));
            else
                ps.setNull(5, java.sql.Types.DATE);

            ps.setInt(6, rental.getRentalDurationDays());
            ps.setBigDecimal(7, rental.getTotalFee());

            if (rental.getPaymentDueDate() != null)
                ps.setDate(8, java.sql.Date.valueOf(rental.getPaymentDueDate()));
            else
                ps.setNull(8, java.sql.Types.DATE);

            ps.setString(9, rental.getAdditionalItems());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental 저장 실패", e);
        }
    }

    public List<Rental> findByCustomerId(Long customerId) {
        String sql = "SELECT * FROM Rental WHERE customer_id = ?";
        List<Rental> rentals = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Rental rental = new Rental(
                        rs.getLong("id"),  // 또는 null 허용이면 빼도 됩니다
                        rs.getLong("car_id"),
                        rs.getLong("customer_id"),
                        rs.getLong("company_id"),
                        rs.getString("license_number"),
                        rs.getDate("rental_start_date").toLocalDate(),
                        rs.getInt("rental_duration_days"),
                        rs.getBigDecimal("total_fee"),
                        rs.getDate("payment_due_date").toLocalDate(),
                        rs.getString("additional_items")
                );
                rentals.add(rental);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("customerId로 예약 조회 실패", e);
        }

        return rentals;
    }

    public List<Rental> findAll() {
        String sql = "SELECT * FROM Rental";
        List<Rental> rentals = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rental rental = new Rental(
                        rs.getLong("rental_id"),
                        rs.getLong("car_id"),
                        rs.getLong("customer_id"),
                        rs.getLong("company_id"),
                        rs.getString("license_number"),
                        rs.getDate("rental_start_date") != null ? rs.getDate("rental_start_date").toLocalDate() : null,
                        rs.getInt("rental_duration_days"),
                        rs.getBigDecimal("total_fee"),
                        rs.getDate("payment_due_date") != null ? rs.getDate("payment_due_date").toLocalDate() : null,
                        rs.getString("additional_items")
                );
                rentals.add(rental);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental 목록 조회 실패", e);
        }

        return rentals;
    }

    public void update(Rental rental) {
        String sql = """
        UPDATE Rental SET
            car_id = ?, customer_id = ?, company_id = ?, license_number = ?,
            rental_start_date = ?, rental_duration_days = ?, total_fee = ?,
            payment_due_date = ?, additional_items = ?
        WHERE id = ?
    """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, rental.getCarId());
            ps.setLong(2, rental.getCustomerId());
            ps.setLong(3, rental.getCompanyId());
            ps.setString(4, rental.getLicenseNumber());

            if (rental.getRentalStartDate() != null)
                ps.setDate(5, java.sql.Date.valueOf(rental.getRentalStartDate()));
            else
                ps.setNull(5, java.sql.Types.DATE);

            ps.setInt(6, rental.getRentalDurationDays());
            ps.setBigDecimal(7, rental.getTotalFee());

            if (rental.getPaymentDueDate() != null)
                ps.setDate(8, java.sql.Date.valueOf(rental.getPaymentDueDate()));
            else
                ps.setNull(8, java.sql.Types.DATE);

            ps.setString(9, rental.getAdditionalItems());
            ps.setLong(10, rental.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental 수정 실패", e);
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
        String sql = "DELETE FROM Rental WHERE id = ?";

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental 삭제 실패", e);
        }
    }

    public void deleteBySql(String sql) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental 삭제 실패", e);
        }
    }

    public Vector<String> getColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Car ID");
        columnNames.add("Customer ID");
        columnNames.add("Company ID");
        columnNames.add("Rental Start Date");
        columnNames.add("Rental Duration (Days)");
        columnNames.add("Total Fee");
        columnNames.add("Payment Due Date");
        columnNames.add("Additional Items");
        return columnNames;
    }

    public Vector<Vector<Object>> getTableData() {
        List<Rental> rentals = findAll(); // findAll() 메서드가 정의되어 있어야 합니다.
        Vector<Vector<Object>> data = new Vector<>();

        for (Rental rental : rentals) {
            Vector<Object> row = new Vector<>();
            row.add(rental.getId());
            row.add(rental.getCarId());
            row.add(rental.getCustomerId());
            row.add(rental.getCompanyId());
            row.add(rental.getRentalStartDate());
            row.add(rental.getRentalDurationDays());
            row.add(rental.getTotalFee());
            row.add(rental.getPaymentDueDate());
            row.add(rental.getAdditionalItems());
            data.add(row);
        }

        return data;
    }
}
