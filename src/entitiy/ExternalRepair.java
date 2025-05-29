package entitiy;

public class ExternalRepair {

    private Long id;
    private Long carId;
    private Long shopId;
    private Long companyId;
    private String licenseNumber;
    private String repairDetails;
    private String repairDate;
    private String repairCost;
    private String paymentDueDate;

    public ExternalRepair(Long id, Long carId, Long shopId, Long companyId, String licenseNumber, String repairDetails, String repairDate, String repairCost, String paymentDueDate) {
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

    public ExternalRepair(Long carId, Long shopId, Long companyId, String licenseNumber, String repairDetails, String repairDate, String repairCost, String paymentDueDate) {
        this.carId = carId;
        this.shopId = shopId;
        this.companyId = companyId;
        this.licenseNumber = licenseNumber;
        this.repairDetails = repairDetails;
        this.repairDate = repairDate;
        this.repairCost = repairCost;
        this.paymentDueDate = paymentDueDate;
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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

    public String getRepairDetails() {
        return repairDetails;
    }

    public void setRepairDetails(String repairDetails) {
        this.repairDetails = repairDetails;
    }

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public String getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(String repairCost) {
        this.repairCost = repairCost;
    }

    public String getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(String paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }
}
