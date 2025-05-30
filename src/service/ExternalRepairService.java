package service;

import dao.ExternalRepairDao;
import entitiy.ExternalRepair;

import java.time.LocalDate;
import java.util.List;

public class ExternalRepairService {

    private final ExternalRepairDao externalRepairDao;

    public ExternalRepairService(ExternalRepairDao externalRepairDao) {
        this.externalRepairDao = externalRepairDao;
    }

    public void save(ExternalRepair externalRepair) {
        externalRepairDao.save(externalRepair);
    }

    public List<ExternalRepair> findAll() {
        return externalRepairDao.findAll();
    }

    public void update(ExternalRepair externalRepair) {
        externalRepairDao.update(externalRepair);
    }

    public void updateBySql(String sql) {
        externalRepairDao.updateBySql(sql);
    }

    public void delete(Long id) {
        externalRepairDao.delete(id);
    }

    public void deleteBySql(String sql) {
        externalRepairDao.deleteBySql(sql);
    }


}
