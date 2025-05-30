package dao;

import connnection.DBConnectionUtil;
import entitiy.Rental;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDate;


public class RentalDao {
    public List<Rental> findByCustomerId(Long customerId) {
        String sql = "SELECT * FROM Rental WHERE customer_id = ?";
        List<Rental> rentals = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental 조회 실패", e);
        }
        return rentals;
    }
    public void updateRentalPeriod(Long rentalId, String rentalStartDate, int rentalDuration,
                                   String additionalItems, BigDecimal totalFee, LocalDate paymentDueDate) {
        String sql = "UPDATE Rental SET rental_start_date=?, rental_duration_days=?, " +
                "additional_items=?, total_fee=?, payment_due_date=? WHERE rental_id=?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(rentalStartDate));
            ps.setInt(2, rentalDuration);
            ps.setString(3, additionalItems);
            ps.setBigDecimal(4, totalFee);
            ps.setDate(5, Date.valueOf(paymentDueDate));
            ps.setLong(6, rentalId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Rental 기간 수정 실패", e);
        }
    }

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
                ps.setDate(5, Date.valueOf(rental.getRentalStartDate()));
            else
                ps.setNull(5, Types.DATE);

            ps.setInt(6, rental.getRentalDurationDays());
            ps.setBigDecimal(7, rental.getTotalFee());

            if (rental.getPaymentDueDate() != null)
                ps.setDate(8, Date.valueOf(rental.getPaymentDueDate()));
            else
                ps.setNull(8, Types.DATE);

            ps.setString(9, rental.getAdditionalItems());
            System.out.println("저장할 license_number: [" + rental.getLicenseNumber() + "]");

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental 저장 실패", e);
        }
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
                ps.setDate(5, Date.valueOf(rental.getRentalStartDate()));
            else
                ps.setNull(5, Types.DATE);

            ps.setInt(6, rental.getRentalDurationDays());
            ps.setBigDecimal(7, rental.getTotalFee());

            if (rental.getPaymentDueDate() != null)
                ps.setDate(8, Date.valueOf(rental.getPaymentDueDate()));
            else
                ps.setNull(8, Types.DATE);

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
        String sql = "DELETE FROM Rental WHERE rental_id = ?";

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental 삭제 실패", e);
        }
    }
    public void updateRentalPeriod(Long rentalId, String rentalStartDate, int rentalDuration, String additionalItems) {
        String sql = "UPDATE Rental SET rental_start_date = ?, rental_duration_days = ?, additional_items = ? WHERE rental_id = ?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rentalStartDate);
            ps.setInt(2, rentalDuration);
            ps.setString(3, additionalItems);
            ps.setLong(4, rentalId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental 수정 실패", e);
        }
    }
    public void updateRentalCar(Long rentalId, Long carId, String rentalStartDate, int rentalDuration,
                                String additionalItems, BigDecimal totalFee, LocalDate paymentDueDate) {
        String sql = "UPDATE Rental SET car_id=?, rental_start_date=?, rental_duration_days=?, " +
                "additional_items=?, total_fee=?, payment_due_date=? WHERE rental_id=?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, carId);
            ps.setDate(2, Date.valueOf(rentalStartDate));
            ps.setInt(3, rentalDuration);
            ps.setString(4, additionalItems);
            ps.setBigDecimal(5, totalFee);
            ps.setDate(6, Date.valueOf(paymentDueDate));
            ps.setLong(7, rentalId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Rental 수정 실패", e);
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
    public List<Rental> findByCarId(Long carId) {
        String sql = "SELECT * FROM Rental WHERE car_id = ?";
        List<Rental> rentals = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, carId);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Rental(차량별) 조회 실패", e);
        }
        return rentals;
    }
    public boolean isDateRangeOverlapped(Long carId, LocalDate start, LocalDate end) {
        String sql = """
    SELECT COUNT(*) FROM Rental
    WHERE car_id = ?
      AND (
        rental_start_date <= ? -- 신규 끝
        AND DATE_ADD(rental_start_date, INTERVAL rental_duration_days - 1 DAY) >= ? -- 신규 시작
      )
    """;
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, carId);
            ps.setDate(2, Date.valueOf(end));
            ps.setDate(3, Date.valueOf(start));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("대여 가능 여부 체크 실패", e);
        }
        return false;
    }
    public Rental findById(Long id) {
        String sql = "SELECT * FROM Rental WHERE rental_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rental rental = new Rental();
                    rental.setId(rs.getLong("rental_id"));
                    rental.setCarId(rs.getLong("car_id"));
                    rental.setCustomerId(rs.getLong("customer_id"));
                    rental.setCompanyId(rs.getLong("company_id"));
                    rental.setLicenseNumber(rs.getString("license_number"));
                    rental.setRentalStartDate(rs.getDate("rental_start_date").toLocalDate());
                    rental.setRentalDurationDays(rs.getInt("rental_duration_days"));
                    rental.setTotalFee(rs.getBigDecimal("total_fee"));
                    rental.setPaymentDueDate(rs.getDate("payment_due_date").toLocalDate());
                    rental.setAdditionalItems(rs.getString("additional_items"));
                    return rental;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
