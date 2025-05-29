package service;

import dao.DeleteDao;

public class DeleteService {

    private final DeleteDao deleteDao;

    public DeleteService(DeleteDao deleteDao) {
        this.deleteDao = deleteDao;
    }

    public int delete(String sql) {
        return deleteDao.executeDelete(sql);
    }
}
