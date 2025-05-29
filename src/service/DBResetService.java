package service;

import connnection.DBConnectionUtil;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBResetService {

    public void init() {

        try (Connection con = DBConnectionUtil.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE TABLE CampingCarCompany");
            stmt.execute("TRUNCATE TABLE CampingCar");
            stmt.execute("TRUNCATE TABLE Customer");
            stmt.execute("TRUNCATE TABLE ExternalRepair");
            stmt.execute("TRUNCATE TABLE MaintenanceRecord");
            stmt.execute("TRUNCATE TABLE Part");
            stmt.execute("TRUNCATE TABLE Rental");
            stmt.execute("TRUNCATE TABLE RepairShop");
            stmt.execute("TRUNCATE TABLE Staff");

            String insertSql = readSqlFromClasspath("reset_data.sql");
            for (String query : insertSql.split(";")) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query.trim());
                }
            }

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("DB 초기화 실패", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readSqlFromClasspath(String resourcePath) throws IOException {
        InputStream is = DBResetService.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new FileNotFoundException("리소스 파일을 찾을 수 없습니다: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}
