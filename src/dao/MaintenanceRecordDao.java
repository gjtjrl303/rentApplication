package dao;

import connnection.DBConnectionUtil;
import dto.MaintenanceRecordDetail;
import entitiy.MaintenanceRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MaintenanceRecordDao {

    public void save(MaintenanceRecord record) {
        String sql = """
                INSERT INTO MaintenanceRecord (
                    car_id, part_id, Staff_staff_id, maintenance_date, duration_minutes
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, record.getCarId());
            ps.setLong(2, record.getPartId());
            ps.setLong(3, record.getStaffId());
            ps.setDate(4, java.sql.Date.valueOf(record.getMaintenanceDate()));
            ps.setInt(5, record.getDurationMinutes());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("정비 기록 저장 실패", e);
        }
    }

    public List<MaintenanceRecord> findAll() {
        String sql = "SELECT * FROM MaintenanceRecord";
        List<MaintenanceRecord> records = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MaintenanceRecord record = new MaintenanceRecord(
                        rs.getLong("maintenance_id"),
                        rs.getLong("car_id"),
                        rs.getLong("part_id"),
                        rs.getLong("staff_id"),
                        rs.getDate("maintenance_date").toLocalDate(),
                        rs.getInt("duration_minutes")
                );
                records.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("정비 기록 조회 실패", e);
        }

        return records;
    }

    public void update(MaintenanceRecord record) {
        String sql = """
                    UPDATE MaintenanceRecord SET
                        car_id = ?, part_id = ?, Staff_staff_id = ?, maintenance_date = ?, duration_minutes = ?
                    WHERE id = ?
                """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, record.getCarId());
            ps.setLong(2, record.getPartId());
            ps.setLong(3, record.getStaffId());
            ps.setDate(4, java.sql.Date.valueOf(record.getMaintenanceDate()));
            ps.setInt(5, record.getDurationMinutes());
            ps.setLong(6, record.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("정비 기록 수정 실패", e);
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
        String sql = "DELETE FROM MaintenanceRecord WHERE id = ?";

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("정비 기록 삭제 실패", e);
        }
    }

    public void deleteBySql(String sql) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("정비 기록 삭제 실패", e);
        }
    }

    public List<MaintenanceRecordDetail> findMaintenanceRecordDetailsByCarId(Long carId) {
        String sql = """
                SELECT
                    m.maintenance_id AS maintenance_id,
                    m.maintenance_date,
                    m.duration_minutes,
                    p.part_name AS part_name,
                    p.stock_quantity,
                    p.unit_price,
                    p.arrival_date,
                    p.supplier_name,
                    s.name AS staff_name
                FROM MaintenanceRecord m
                JOIN Part p ON m.part_id = p.part_id
                JOIN Staff s ON m.staff_id = s.staff_id
                WHERE m.car_id = ?
                ORDER BY m.maintenance_date DESC
                """;

        List<MaintenanceRecordDetail> result = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, carId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MaintenanceRecordDetail detail = new MaintenanceRecordDetail(
                        rs.getLong("maintenance_id"),
                        rs.getDate("maintenance_date").toLocalDate(),
                        rs.getInt("duration_minutes"),
                        rs.getString("part_name"),
                        rs.getInt("stock_quantity"),
                        rs.getBigDecimal("unit_price"),
                        rs.getDate("arrival_date").toLocalDate(),
                        rs.getString("supplier_name"),
                        rs.getString("staff_name")
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
        columnNames.add("Part ID");
        columnNames.add("Staff ID");
        columnNames.add("Maintenance Date");
        columnNames.add("Duration (minutes)");
        return columnNames;
    }

    public Vector<Vector<Object>> getTableData() {
        List<MaintenanceRecord> records = findAll(); // findAll() 메서드가 있어야 함
        Vector<Vector<Object>> data = new Vector<>();

        for (MaintenanceRecord record : records) {
            Vector<Object> row = new Vector<>();
            row.add(record.getId());
            row.add(record.getCarId());
            row.add(record.getPartId());
            row.add(record.getStaffId());
            row.add(record.getMaintenanceDate());
            row.add(record.getDurationMinutes());
            data.add(row);
        }

        return data;
    }
}
