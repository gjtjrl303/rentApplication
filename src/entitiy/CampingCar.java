package entitiy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CampingCar {

    private Long id;
    private Long companyId;
    private String carName;
    private String licensePlate;
    private int capacity;
    private String imageUrl;
    private String description;
    private BigDecimal rentalPrice;
    private LocalDate registrationDate;

    public CampingCar(Long id, Long companyId, String carName, String licensePlate, int capacity, String imageUrl, String description, BigDecimal rentalPrice, LocalDate registrationDate) {
        this.id = id;
        this.companyId = companyId;
        this.carName = carName;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.imageUrl = imageUrl;
        this.description = description;
        this.rentalPrice = rentalPrice;
        this.registrationDate = registrationDate;
    }

    public CampingCar(Long companyId, String carName, String licensePlate, int capacity, String imageUrl, String description, BigDecimal rentalPrice, LocalDate registrationDate) {
        this.companyId = companyId;
        this.carName = carName;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.imageUrl = imageUrl;
        this.description = description;
        this.rentalPrice = rentalPrice;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "CampingCar{" +
                ", carName='" + carName + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", capacity=" + capacity +
                ", rentalPrice=" + rentalPrice +
                '}';
    }
}
