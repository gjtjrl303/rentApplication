package dao;

import connnection.DBConnectionUtil;
import dto.ExternalRepairDetail;
import entitiy.ExternalRepair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ExternalRepairDao {

    public void save(ExternalRepair repair) {
        String sql = """
                INSERT INTO ExternalRepair (
                    car_id, shop_id, compay_id, license_number,
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
            ps.setString(6, repair.getRepairDate());
            ps.setString(7, repair.getRepairCost());
            ps.setString(8, repair.getPaymentDueDate());

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
                        rs.getLong("repair_id"),
                        rs.getLong("car_id"),
                        rs.getLong("shop_id"),
                        rs.getLong("company_id"),
                        rs.getString("license_number"),
                        rs.getString("repair_details"),
                        rs.getString("repair_date"),
                        rs.getString("repair_cost"),
                        rs.getString("payment_due_date")
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
                        car_id = ?, shop_id = ?, compay_id = ?, license_number = ?,
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
            ps.setString(6, repair.getRepairDate());
            ps.setString(7, repair.getRepairCost());
            ps.setString(8, repair.getPaymentDueDate());
            ps.setLong(9, repair.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("ExternalRepair 수정 실패", e);
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

    public List<ExternalRepairDetail> findExternalRepairDetailsByCarId(Long carId) {
        String sql = """
                SELECT
                   er.repair_id AS repair_id,
                   er.repair_date,
                   er.repair_cost,
                   rs.shop_id AS shop_id,
                   rs.name AS shop_name,
                   rs.address,
                   rs.phone
               FROM ExternalRepair er
               JOIN RepairShop rs ON er.shop_id = rs.shop_id
               WHERE er.car_id = ?
                """;

        List<ExternalRepairDetail> result = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, carId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ExternalRepairDetail detail = new ExternalRepairDetail(
                        rs.getLong("repair_id"),
                        rs.getDate("repair_date").toLocalDate(),
                        rs.getBigDecimal("repair_cost"),
                        rs.getString("shop_name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
                result.add(detail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Vector<String> getColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Car ID");
        columnNames.add("Shop ID");
        columnNames.add("Company ID");
        columnNames.add("License Number");
        columnNames.add("Repair Details");
        columnNames.add("Repair Date");
        columnNames.add("Repair Cost");
        columnNames.add("Payment Due Date");
        return columnNames;
    }

    public Vector<Vector<Object>> getTableData() {
        List<ExternalRepair> repairs = findAll(); // findAll()이 있어야 합니다
        Vector<Vector<Object>> data = new Vector<>();

        for (ExternalRepair repair : repairs) {
            Vector<Object> row = new Vector<>();
            row.add(repair.getId());
            row.add(repair.getCarId());
            row.add(repair.getShopId());
            row.add(repair.getCompanyId());
            row.add(repair.getLicenseNumber());
            row.add(repair.getRepairDetails());
            row.add(repair.getRepairDate());
            row.add(repair.getRepairCost());
            row.add(repair.getPaymentDueDate());
            data.add(row);
        }

        return data;
    }
}
