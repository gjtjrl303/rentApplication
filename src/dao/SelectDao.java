package dao;

import connnection.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectDao {

    public List<String[]> executeSelect(String sql) {
        List<String[]> results = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                results.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException("SELECT 쿼리 실행 중 오류 발생", e);
        }

        return results;
    }

    public List<String> getColumnNames(String sql) {
        List<String> columnNames = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

        } catch (SQLException e) {
            throw new RuntimeException("컬럼명 조회 중 오류 발생", e);
        }

        return columnNames;
    }
}
