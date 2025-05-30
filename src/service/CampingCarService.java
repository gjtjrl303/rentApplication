package service;

import dao.CampingCarDao;
import entitiy.CampingCar;

import java.util.List;

public class CampingCarService {

    private final CampingCarDao campingCarDao;

    public CampingCarService(CampingCarDao campingCarDao) {
        this.campingCarDao = campingCarDao;
    }

    public void save(CampingCar campingCar) {
        campingCarDao.save(campingCar);
    }

    public void update(CampingCar campingCar) {
        campingCarDao.update(campingCar);
    }

    public void updateBySql(String sql) {
        campingCarDao.updateBySql(sql);
    }

    public List<CampingCar> findAll() {
        return campingCarDao.findAll(); // DAO도 동일하게 반환해야 함
    }
    // CampingCarService.java
    public List<CampingCar> findAllIncludingRented() {
        return campingCarDao.findAllIncludingRented();
    }

    public void delete(Long id) {
        campingCarDao.delete(id);
    }

    public void deleteBySql(String sql) {
        campingCarDao.deleteBySql(sql);
    }

}
