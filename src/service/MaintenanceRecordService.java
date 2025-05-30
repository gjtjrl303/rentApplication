package service;

import dao.MaintenanceRecordDao;
import entitiy.MaintenanceRecord;

import java.util.List;

public class MaintenanceRecordService {

    private final MaintenanceRecordDao maintenanceRecordDao;

    public MaintenanceRecordService(MaintenanceRecordDao maintenanceRecordDao) {
        this.maintenanceRecordDao = maintenanceRecordDao;
    }

    public void save(MaintenanceRecord maintenanceRecord) {
        maintenanceRecordDao.save(maintenanceRecord);
    }

    public List<MaintenanceRecord> findAll() {
        return maintenanceRecordDao.findAll();
    }

    public void update(MaintenanceRecord maintenanceRecord) {
        maintenanceRecordDao.update(maintenanceRecord);
    }

    public void updateBySql(String sql) {
        maintenanceRecordDao.updateBySql(sql);
    }

    public void delete(Long id) {
        maintenanceRecordDao.delete(id);
    }

    public void deleteBySql(String sql) {
        maintenanceRecordDao.deleteBySql(sql);
    }
}
