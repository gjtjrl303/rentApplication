package service;

import dao.CampingCarCompanyDao;
import entitiy.CampingCarCompany;

import java.util.List;

public class CampingCarCompanyService {

    private final CampingCarCompanyDao campingCarCompanyDao;

    public CampingCarCompanyService(CampingCarCompanyDao campingCarCompanyDao) {
        this.campingCarCompanyDao = campingCarCompanyDao;
    }

    public void save(CampingCarCompany campingCarCompany) {
        campingCarCompanyDao.save(campingCarCompany);
    }

    public List<CampingCarCompany> findAll() {
        return campingCarCompanyDao.findAll();
    }

    public void update(CampingCarCompany campingCarCompany) {
        campingCarCompanyDao.update(campingCarCompany);
    }

    public void updateBySql(String sql) {
        campingCarCompanyDao.updateBySql(sql);
    }

    public void delete(Long id) {
        campingCarCompanyDao.delete(id);
    }

    public void deleteBySql(String sql) {
        campingCarCompanyDao.deleteBySql(sql);
    }
}
