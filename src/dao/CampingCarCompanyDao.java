package dao;

import connnection.DBConnectionUtil;
import entitiy.CampingCarCompany;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CampingCarCompanyDao {

    public void save(CampingCarCompany campingCarCompany) {

        String sql = """
                INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) 
                VALUES(?,?,?,?,?)
                """;
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, campingCarCompany.getName());
            ps.setString(2, campingCarCompany.getAddress());
            ps.setString(3, campingCarCompany.getPhone());
            ps.setString(4, campingCarCompany.getManagerName());
            ps.setString(5, campingCarCompany.getManagerEmail());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public void update(CampingCarCompany company) {
        String sql = """
            UPDATE CampingCarCompany
            SET name = ?, address = ?, phone = ?, manager_name = ?, manager_email = ?
            WHERE id = ?
            """;
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, company.getName());
            ps.setString(2, company.getAddress());
            ps.setString(3, company.getPhone());
            ps.setString(4, company.getManagerName());
            ps.setString(5, company.getManagerEmail());
            ps.setLong(6, company.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("업데이트 실패");
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

    public List<CampingCarCompany> findAll() {
        String sql = "SELECT * FROM CampingCarCompany";
        List<CampingCarCompany> companies = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                CampingCarCompany company = new CampingCarCompany(
                        rs.getLong(  "company_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("manager_name"),
                        rs.getString("manager_email")
                );
                companies.add(company);
            }
            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("전체 조회 실패");
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM CampingCarCompany WHERE id = ?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("삭제 실패");
        }
    }

    public void deleteBySql(String sql) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("삭제 실패");
        }
    }



}
