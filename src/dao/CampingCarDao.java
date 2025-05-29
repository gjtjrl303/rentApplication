package dao;

import connnection.DBConnectionUtil;
import entitiy.CampingCar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CampingCarDao {

    public void save(CampingCar campingCar) {

        String sql = """
                INSERT INTO CampingCar (
                                             company_id,
                                               car_name,
                                               license_plate,
                                               capacity,
                                               image_url,
                                               description,
                                               rental_price,
                                              registration_date) 
                VALUES(?,?,?,?,?,?,?,?)
                """;
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setLong(1, campingCar.getCompanyId());
            ps.setString(2, campingCar.getCarName());
            ps.setString(3, campingCar.getLicensePlate());
            ps.setInt(4, campingCar.getCapacity());
            ps.setString(5, campingCar.getImageUrl());
            ps.setString(6, campingCar.getDescription());
            ps.setBigDecimal(7, campingCar.getRentalPrice());
            ps.setDate(8 , Date.valueOf(campingCar.getRegistrationDate()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public List<CampingCar> findAll() {
        String sql = "SELECT * FROM CampingCar";
        List<CampingCar> cars = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                CampingCar car = new CampingCar(
                        rs.getLong("car_id"),
                        rs.getLong("company_id"),
                        rs.getString("car_name"),
                        rs.getString("license_plate"),
                        rs.getInt("capacity"),
                        rs.getString("image_url"),
                        rs.getString("description"),
                        rs.getBigDecimal("rental_price"),
                        rs.getDate("registration_date").toLocalDate()
                );
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

        return cars;
    }

    public void update(CampingCar campingCar) {
        String sql = """
            UPDATE CampingCar SET
                company_id = ?,
                car_name = ?,
                license_plate = ?,
                capacity = ?,
                image_url = ?,
                description = ?,
                rental_price = ?,
                registrationDate = ?
            WHERE id = ?
            """;
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setLong(1, campingCar.getCompanyId());
            ps.setString(2, campingCar.getCarName());
            ps.setString(3, campingCar.getLicensePlate());
            ps.setInt(4, campingCar.getCapacity());
            ps.setString(5, campingCar.getImageUrl());
            ps.setString(6, campingCar.getDescription());
            ps.setBigDecimal(7, campingCar.getRentalPrice());
            ps.setDate(8, Date.valueOf(campingCar.getRegistrationDate()));
            ps.setLong(9, campingCar.getId());  // id는 WHERE 조건
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
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
        String sql = "DELETE FROM CampingCar WHERE id = ?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public void deleteBySql(String sql) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public Vector<String> getColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Company ID");
        columnNames.add("Car Name");
        columnNames.add("License Plate");
        columnNames.add("Capacity");
        columnNames.add("Image URL");
        columnNames.add("Description");
        columnNames.add("Rental Price");
        columnNames.add("Registration Date");
        return columnNames;
    }

    public Vector<Vector<Object>> getTableData() {
        List<CampingCar> cars = findAll(); // CampingCarDao에 이미 존재하는 메서드라고 가정
        Vector<Vector<Object>> data = new Vector<>();

        for (CampingCar car : cars) {
            Vector<Object> row = new Vector<>();
            row.add(car.getId());
            row.add(car.getCompanyId());
            row.add(car.getCarName());
            row.add(car.getLicensePlate());
            row.add(car.getCapacity());
            row.add(car.getImageUrl());
            row.add(car.getDescription());
            row.add(car.getRentalPrice());
            row.add(car.getRegistrationDate());
            data.add(row);
        }

        return data;
    }
}
