package entitiy;

import java.time.LocalDate;

public class ExternalRepair {
    private Long id;
    private Long carId;
    private Long shopId;
    private Long companyId;
    private String licenseNumber;
    private String repairDetails;
    private LocalDate repairDate;
    private Double repairCost;
    private LocalDate paymentDueDate;

    public ExternalRepair(Long id, Long carId, Long shopId, Long companyId, String licenseNumber,
                          String repairDetails, LocalDate repairDate, Double repairCost, LocalDate paymentDueDate) {
        this.id = id;
        this.carId = carId;
        this.shopId = shopId;
        this.companyId = companyId;
        this.licenseNumber = licenseNumber;
        this.repairDetails = repairDetails;
        this.repairDate = repairDate;
        this.repairCost = repairCost;
        this.paymentDueDate = paymentDueDate;
    }

    public Long getId() { return id; }
    public Long getCarId() { return carId; }
    public Long getShopId() { return shopId; }
    public Long getCompanyId() { return companyId; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getRepairDetails() { return repairDetails; }
    public LocalDate getRepairDate() { return repairDate; }
    public Double getRepairCost() { return repairCost; }
    public LocalDate getPaymentDueDate() { return paymentDueDate; }
}
