package dao;

import connnection.DBConnectionUtil;
import entitiy.CampingCar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "SELECT cc.*, ccc.name AS company_name " +
                "FROM CampingCar cc " +
                "JOIN CampingCarCompany ccc ON cc.company_id = ccc.company_id";
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
                car.setCompanyName(rs.getString("company_name"));

                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

        return cars;
    }
    public List<CampingCar> findAllIncludingRented() {
        String sql = "SELECT cc.*, ccc.name AS company_name\n" +
                "FROM CampingCar cc\n" +
                "JOIN CampingCarCompany ccc ON cc.company_id = ccc.company_id";
        List<CampingCar> cars = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CampingCar car = new CampingCar(
                        rs.getLong("car_id"),       // 반드시 car_id
                        rs.getLong("company_id"),
                        rs.getString("car_name"),
                        rs.getString("license_plate"),
                        rs.getInt("capacity"),
                        rs.getString("image_url"),
                        rs.getString("description"),
                        rs.getBigDecimal("rental_price"),
                        rs.getDate("registration_Date").toLocalDate()
                );
                car.setCompanyName(rs.getString("company_name"));
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
                registration_date = ?
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
    public CampingCar findById(Long carId) {
        String sql = "SELECT * FROM CampingCar WHERE car_id = ?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, carId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CampingCar(
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("CampingCar 조회 실패", e);
        }
        return null;
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
}
