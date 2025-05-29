package service;

import dao.StaffDao;
import entitiy.Staff;

import java.util.List;

public class StaffService {

    private final StaffDao staffDao;

    public StaffService(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public void save(Staff staff) {
        staffDao.save(staff);
    }

    public List<Staff> findAll() {
        return staffDao.findAll();
    }

    public void update(Staff staff) {
        staffDao.update(staff);
    }

    public void updateBySql(String sql) {
        staffDao.updateBySql(sql);
    }

    public void delete(Long id) {
        staffDao.delete(id);
    }

    public void deleteBySql(String sql) {
        staffDao.deleteBySql(sql);
    }
}
