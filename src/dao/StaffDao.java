package dao;

import connnection.DBConnectionUtil;
import entitiy.Role;
import entitiy.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StaffDao {

    public void save(Staff staff) {
        String sql = """
        INSERT INTO Staff (
            name, phone, address, monthly_salary, num_dependents, department, role
        ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, staff.getName());
            ps.setString(2, staff.getPhone());
            ps.setString(3, staff.getAddress());
            ps.setBigDecimal(4, staff.getMonthlySalary());
            ps.setInt(5, staff.getNumDependents());
            ps.setString(6, staff.getDepartment());
            ps.setString(7, staff.getRole().name()); // ENUM 값 문자열로 삽입

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("직원 저장 실패", e);
        }
    }

    public List<Staff> findAll() {
        String sql = "SELECT * FROM Staff";
        List<Staff> staffList = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Staff staff = new Staff(
                        rs.getLong("staff_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBigDecimal("monthly_salary"),
                        rs.getInt("num_dependents"),
                        rs.getString("department"),
                        Role.valueOf(rs.getString("role")) // ENUM 변환
                );
                staffList.add(staff);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("직원 목록 조회 실패", e);
        }

        return staffList;
    }

    public void update(Staff staff) {
        String sql = """
        UPDATE Staff SET
            name = ?, phone = ?, address = ?, monthly_salary = ?, 
            num_dependents = ?, department = ?, role = ?
        WHERE id = ?
    """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, staff.getName());
            ps.setString(2, staff.getPhone());
            ps.setString(3, staff.getAddress());
            ps.setBigDecimal(4, staff.getMonthlySalary());
            ps.setInt(5, staff.getNumDependents());
            ps.setString(6, staff.getDepartment());
            ps.setString(7, staff.getRole().name());
            ps.setLong(8, staff.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("직원 수정 실패", e);
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
        String sql = "DELETE FROM Staff WHERE id = ?";

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("직원 삭제 실패", e);
        }
    }

    public void deleteBySql(String sql) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("직원 삭제 실패", e);
        }
    }

    public Vector<String> getColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Phone");
        columnNames.add("Address");
        columnNames.add("Monthly Salary");
        columnNames.add("Num Dependents");
        columnNames.add("Department");
        columnNames.add("Role");
        return columnNames;
    }

    public Vector<Vector<Object>> getTableData() {
        List<Staff> staffList = findAll(); // findAll()이 미리 구현되어 있어야 함
        Vector<Vector<Object>> data = new Vector<>();

        for (Staff staff : staffList) {
            Vector<Object> row = new Vector<>();
            row.add(staff.getId());
            row.add(staff.getName());
            row.add(staff.getPhone());
            row.add(staff.getAddress());
            row.add(staff.getMonthlySalary());
            row.add(staff.getNumDependents());
            row.add(staff.getDepartment());
            row.add(staff.getRole().name()); // enum을 문자열로
            data.add(row);
        }

        return data;
    }
}
