package service;

import dao.RentalDao;
import entitiy.Rental;

import java.util.List;

public class RentalService {

    private final RentalDao rentalDao;

    public RentalService(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    public void save(Rental rental) {
        rentalDao.save(rental);
    }

    public List<Rental> findAll() {
        return rentalDao.findAll();
    }

    public void update(Rental rental) {
        rentalDao.update(rental);
    }

    public void updateBySql(String sql) {
        rentalDao.updateBySql(sql);
    }

    public void delete(Long id) {
        rentalDao.delete(id);
    }

    public void deleteBySql(String sql) {
        rentalDao.deleteBySql(sql);
    }
}
