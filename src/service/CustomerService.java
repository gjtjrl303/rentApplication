package service;

import dao.CustomerDao;
import entitiy.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void save(Customer customer) {
        customerDao.save(customer);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public void update(Customer customer) {
        customerDao.update(customer);
    }

    public void updateBySql(String sql) {
        customerDao.updateBySql(sql);
    }

    public void delete(Long id) {
        customerDao.delete(id);
    }

    public void deleteBySql(String sql) {
        customerDao.deleteBySql(sql);
    }


}
