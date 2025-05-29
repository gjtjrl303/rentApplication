package service;

import dao.SelectDao;

import java.util.List;

public class SelectService {

    private final SelectDao selectDao;

    public SelectService(SelectDao selectDao) {
        this.selectDao = selectDao;
    }

    public List<String[]> executeSelect(String sql) {
        return selectDao.executeSelect(sql);
    }

    public List<String> getColumnNames(String sql) {
        return selectDao.getColumnNames(sql);
    }
}
