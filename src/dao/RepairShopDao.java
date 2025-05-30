package dao;

import connnection.DBConnectionUtil;
import entitiy.RepairShop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairShopDao {

    public void save(RepairShop shop) {
        String sql = """
        INSERT INTO RepairShop (
            name, address, phone, manager_name, manager_email
        )
        VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, shop.getName());
            ps.setString(2, shop.getAddress());
            ps.setString(3, shop.getPhone());
            ps.setString(4, shop.getManagerName());
            ps.setString(5, shop.getManagerEmail());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("RepairShop 저장 실패", e);
        }
    }

    public List<RepairShop> findAll() {
        String sql = "SELECT * FROM RepairShop";
        List<RepairShop> shops = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RepairShop shop = new RepairShop(
                        rs.getLong("shop_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("manager_name"),
                        rs.getString("manager_email")
                );
                shops.add(shop);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("RepairShop 목록 조회 실패", e);
        }

        return shops;
    }

    public void update(RepairShop shop) {
        String sql = """
        UPDATE RepairShop SET
            name = ?, address = ?, phone = ?, manager_name = ?, manager_email = ?
        WHERE id = ?
    """;

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, shop.getName());
            ps.setString(2, shop.getAddress());
            ps.setString(3, shop.getPhone());
            ps.setString(4, shop.getManagerName());
            ps.setString(5, shop.getManagerEmail());
            ps.setLong(6, shop.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("RepairShop 수정 실패", e);
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
        String sql = "DELETE FROM RepairShop WHERE id = ?";

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("RepairShop 삭제 실패", e);
        }
    }

    public void deleteBySql(String sql) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("RepairShop 삭제 실패", e);
        }
    }
}
