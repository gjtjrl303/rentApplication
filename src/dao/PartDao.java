package dao;

import connnection.DBConnectionUtil;
import entitiy.Part;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PartDao {

    public void save(Part part) {
        String sql = """
            INSERT INTO Part (
                part_name,
                unit_price,
                stock_quantity,
                arrival_date,
                supplier_name
            )
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, part.getPartName());
            ps.setBigDecimal(2, part.getUnitPrice());
            ps.setInt(3, part.getStockQuantity());
            ps.setDate(4, java.sql.Date.valueOf(part.getArrivalDate()));
            ps.setString(5, part.getSupplierName());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Part 저장 실패", e);
        }
    }

    public List<Part> findAll() {
        String sql = "SELECT * FROM Part";
        List<Part> parts = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Part part = new Part(
                        rs.getLong("part_id"),
                        rs.getString("part_name"),
                        rs.getBigDecimal("unit_price"),
                        rs.getInt("stock_quantity"),
                        rs.getDate("arrival_date").toLocalDate(),
                        rs.getString("supplier_name")
                );
                parts.add(part);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("부품 조회 실패", e);
        }

        return parts;
    }

    public void update(Part part) {
        String sql = """
        UPDATE Part SET
            part_name = ?, unit_price = ?, stock_quantity = ?, arrival_date = ?, supplier_name = ?
        WHERE id = ?
    """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, part.getPartName());
            ps.setBigDecimal(2, part.getUnitPrice());
            ps.setInt(3, part.getStockQuantity());
            ps.setDate(4, java.sql.Date.valueOf(part.getArrivalDate()));
            ps.setString(5, part.getSupplierName());
            ps.setLong(6, part.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("부품 수정 실패", e);
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
        String sql = "DELETE FROM Part WHERE id = ?";

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("부품 삭제 실패", e);
        }
    }

    public void deleteBySql(String sql) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("부품 삭제 실패", e);
        }
    }

    public Vector<String> getColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Part Name");
        columnNames.add("Unit Price");
        columnNames.add("Stock Quantity");
        columnNames.add("Arrival Date");
        columnNames.add("Supplier Name");
        return columnNames;
    }

    public Vector<Vector<Object>> getTableData() {
        List<Part> parts = findAll(); // findAll() 메서드가 정의되어 있어야 합니다.
        Vector<Vector<Object>> data = new Vector<>();

        for (Part part : parts) {
            Vector<Object> row = new Vector<>();
            row.add(part.getId());
            row.add(part.getPartName());
            row.add(part.getUnitPrice());
            row.add(part.getStockQuantity());
            row.add(part.getArrivalDate());
            row.add(part.getSupplierName());
            data.add(row);
        }

        return data;

    }
}
