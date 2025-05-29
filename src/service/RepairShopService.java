package service;

import dao.RepairShopDao;
import entitiy.RepairShop;

import java.util.List;

public class RepairShopService {

    private final RepairShopDao repairShopDao;

    public RepairShopService(RepairShopDao repairShopDao) {
        this.repairShopDao = repairShopDao;
    }

    public void save(RepairShop repairShop) {
        repairShopDao.save(repairShop);
    }

    public List<RepairShop> findAll() {
        return repairShopDao.findAll();
    }

    public void update(RepairShop repairShop) {
        repairShopDao.update(repairShop);
    }


    public void updateBySql(String sql) {
        repairShopDao.updateBySql(sql);
    }

    public void delete(Long id) {
        repairShopDao.delete(id);
    }

    public void deleteBySql(String sql) {
        repairShopDao.deleteBySql(sql);
    }
}
