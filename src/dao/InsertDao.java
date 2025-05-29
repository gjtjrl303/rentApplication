package dao;

import connnection.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDao {

    public int executeInsert(String sql) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("INSERT 쿼리 실행 중 오류 발생", e);
        }
    }
}
