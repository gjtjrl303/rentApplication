package entitiy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Rental {


    private Long id;
    private Long carId;
    private Long customerId;
    private Long companyId;
    private String licenseNumber;
    private LocalDate rentalStartDate;
    private int rentalDurationDays;
    private BigDecimal totalFee;
    private LocalDate paymentDueDate;
    private String additionalItems;

    public Rental() {}

    public Rental(Long id, Long carId, Long customerId, Long companyId, String licenseNumber, LocalDate rentalStartDate, int rentalDurationDays, BigDecimal totalFee, LocalDate paymentDueDate, String additionalItems) {
        this.id = id;
        this.carId = carId;
        this.customerId = customerId;
        this.companyId = companyId;
        this.licenseNumber = licenseNumber;
        this.rentalStartDate = rentalStartDate;
        this.rentalDurationDays = rentalDurationDays;
        this.totalFee = totalFee;
        this.paymentDueDate = paymentDueDate;
        this.additionalItems = additionalItems;
    }

    public Rental(Long carId, Long customerId, Long companyId, String licenseNumber, LocalDate rentalStartDate, int rentalDurationDays, BigDecimal totalFee, LocalDate paymentDueDate, String additionalItems) {
        this.carId = carId;
        this.customerId = customerId;
        this.companyId = companyId;
        this.licenseNumber = licenseNumber;
        this.rentalStartDate = rentalStartDate;
        this.rentalDurationDays = rentalDurationDays;
        this.totalFee = totalFee;
        this.paymentDueDate = paymentDueDate;
        this.additionalItems = additionalItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public int getRentalDurationDays() {
        return rentalDurationDays;
    }

    public void setRentalDurationDays(int rentalDurationDays) {
        this.rentalDurationDays = rentalDurationDays;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getAdditionalItems() {
        return additionalItems;
    }

    public void setAdditionalItems(String additionalItems) {
        this.additionalItems = additionalItems;
    }

}
