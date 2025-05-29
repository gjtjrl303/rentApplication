package service;

import dao.InsertDao;

public class InsertService {

    private final InsertDao insertDao;

    public InsertService(InsertDao insertDao) {
        this.insertDao = insertDao;
    }

    public int insert(String sql) {
        return insertDao.executeInsert(sql);
    }
}
