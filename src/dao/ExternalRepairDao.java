package dao;

import connnection.DBConnectionUtil;
import entitiy.ExternalRepair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExternalRepairDao {

    public void save(ExternalRepair repair) {
        String sql = """
        INSERT INTO ExternalRepair (
            car_id, shop_id, company_id, license_number,
            repair_details, repair_date, repair_cost, payment_due_date
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, repair.getCarId());
            ps.setLong(2, repair.getShopId());
            ps.setLong(3, repair.getCompanyId());
            ps.setString(4, repair.getLicenseNumber());
            ps.setString(5, repair.getRepairDetails());
            ps.setDate(6, java.sql.Date.valueOf(repair.getRepairDate()));
            ps.setDouble(7, repair.getRepairCost());
            ps.setDate(8, java.sql.Date.valueOf(repair.getPaymentDueDate()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("ExternalRepair 저장 실패", e);
        }
    }

    public List<ExternalRepair> findAll() {
        String sql = "SELECT * FROM ExternalRepair";
        List<ExternalRepair> repairs = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ExternalRepair repair = new ExternalRepair(
                        rs.getLong("id"),
                        rs.getLong("car_id"),
                        rs.getLong("shop_id"),
                        rs.getLong("company_id"),
                        rs.getString("license_number"),
                        rs.getString("repair_details"),
                        rs.getDate("repair_date").toLocalDate(),
                        rs.getDouble("repair_cost"),
                        rs.getDate("payment_due_date").toLocalDate()
                );
                repairs.add(repair);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("ExternalRepair 조회 실패", e);
        }

        return repairs;
    }

    public void update(ExternalRepair repair) {
        String sql = """
        UPDATE ExternalRepair SET
            car_id = ?, shop_id = ?, company_id = ?, license_number = ?,
            repair_details = ?, repair_date = ?, repair_cost = ?, payment_due_date = ?
        WHERE id = ?
        """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, repair.getCarId());
            ps.setLong(2, repair.getShopId());
            ps.setLong(3, repair.getCompanyId());
            ps.setString(4, repair.getLicenseNumber());
            ps.setString(5, repair.getRepairDetails());
            ps.setDate(6, java.sql.Date.valueOf(repair.getRepairDate()));
            ps.setDouble(7, repair.getRepairCost());
            ps.setDate(8, java.sql.Date.valueOf(repair.getPaymentDueDate()));
            ps.setLong(9, repair.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("ExternalRepair 수정 실패", e);
        }
    }

    public void updateBySql(String sql) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("SQL 실행 실패", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM ExternalRepair WHERE id = ?";

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("ExternalRepair 삭제 실패", e);
        }
    }

    public void deleteBySql(String sql) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("ExternalRepair 삭제 실패", e);
        }
    }
}
