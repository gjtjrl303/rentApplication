package service;

import dao.UpdateDao;

public class UpdateService {

    private final UpdateDao updateDao;

    public UpdateService(UpdateDao updateDao) {
        this.updateDao = updateDao;
    }

    public int update(String sql) {
        return updateDao.executeUpdate(sql);
    }
}
