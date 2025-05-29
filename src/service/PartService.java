package service;

import dao.PartDao;
import entitiy.Part;

import java.util.List;

public class PartService {

    private final PartDao partDao;

    public PartService(PartDao partDao) {
        this.partDao = partDao;
    }

    public void save(Part part) {
        partDao.save(part);
    }

    public List<Part> findAll() {
        return partDao.findAll();
    }

    public void update(Part part) {
        partDao.update(part);
    }

    public void updateBySql(String sql) {
        partDao.updateBySql(sql);
    }

    public void delete(Long id) {
        partDao.delete(id);
    }

    public void deleteBySql(String sql) {
        partDao.deleteBySql(sql);
    }
}
