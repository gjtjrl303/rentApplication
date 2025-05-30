package service;

import dao.CampingCarDao;
import dao.RentalDao;
import entitiy.CampingCar;
import entitiy.Rental;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDate;
import java.util.Set;

public class RentalService {

    private final RentalDao rentalDao;
    public void updateRentalPeriod(Long rentalId, String rentalStartDate, int rentalDuration, String additionalItems) {
        // 기존 예약을 찾아야 차량 ID를 얻을 수 있음
        Rental rental = rentalDao.findById(rentalId);
        if (rental == null) {
            throw new IllegalArgumentException("해당 예약을 찾을 수 없습니다: " + rentalId);
        }

        CampingCarDao carDao = new CampingCarDao();
        CampingCar car = carDao.findById(rental.getCarId());
        if (car == null) {
            throw new IllegalArgumentException("해당 차량을 찾을 수 없습니다: " + rental.getCarId());
        }

        BigDecimal totalFee = car.getRentalPrice()
                .multiply(BigDecimal.valueOf(rentalDuration));
        LocalDate paymentDueDate = LocalDate.parse(rentalStartDate).plusDays(rentalDuration);

        rentalDao.updateRentalPeriod(rentalId, rentalStartDate, rentalDuration, additionalItems, totalFee, paymentDueDate);
    }

    public void updateRentalCar(Long rentalId, Long carId, String rentalStartDate, int rentalDuration, String additionalItems) {
        CampingCarDao carDao = new CampingCarDao();
        CampingCar car = carDao.findById(carId);

        if (car == null) {
            throw new IllegalArgumentException("해당 차량을 찾을 수 없습니다: " + carId);
        }

        BigDecimal totalFee = car.getRentalPrice()
                .multiply(BigDecimal.valueOf(rentalDuration));
        LocalDate paymentDueDate = LocalDate.parse(rentalStartDate).plusDays(rentalDuration);

        rentalDao.updateRentalCar(rentalId, carId, rentalStartDate, rentalDuration, additionalItems, totalFee, paymentDueDate);
    }

    // 생성자 위치를 위로!
    public RentalService(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    public List<Rental> findByCustomerId(Long customerId) {
        return rentalDao.findByCustomerId(customerId);
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

    // RentalDao에 이미 findByCarId(Long carId) 추가했다고 가정
    public List<Rental> findByCarId(Long carId) {
        return rentalDao.findByCarId(carId);
    }
    // RentalDao에 isDateRangeOverlapped(Long carId, LocalDate start, LocalDate end) 추가한 경우
    public boolean isDateRangeOverlapped(Long carId, LocalDate start, LocalDate end) {
        return rentalDao.isDateRangeOverlapped(carId, start, end);
    }
    public void save(Rental rental) {
        // rental_price * rental_duration 계산 포함
        CampingCarDao carDao = new CampingCarDao();
        CampingCar car = carDao.findById(rental.getCarId());

        if (car == null) {
            throw new IllegalArgumentException("해당 차량을 찾을 수 없습니다: " + rental.getCarId());
        }

        BigDecimal totalFee = car.getRentalPrice()
                .multiply(BigDecimal.valueOf(rental.getRentalDurationDays()));
        rental.setTotalFee(totalFee);

        rentalDao.save(rental);  // DAO에서 insert 처리
    }

    public Set<LocalDate> getNotAvailableDates(Long carId) {
        List<Rental> rentals = findAll();
        Set<LocalDate> notAvailableDates = new HashSet<>();
        for (Rental rental : rentals) {
            if (rental.getCarId().equals(carId) && rental.getRentalStartDate() != null) {
                LocalDate start = rental.getRentalStartDate();
                int days = rental.getRentalDurationDays();
                for (int i = 0; i < days; i++) {
                    notAvailableDates.add(start.plusDays(i));
                }
            }
        }
        return notAvailableDates;
    }



}
