package dao;

import connnection.DBConnectionUtil;
import entitiy.MaintenanceRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                        rs.getLong("id"),
                        rs.getLong("car_id"),
                        rs.getLong("part_id"),
                        rs.getLong("Staff_staff_id"),
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
}
