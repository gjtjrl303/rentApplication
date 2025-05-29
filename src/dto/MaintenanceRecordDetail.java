package dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MaintenanceRecordDetail {

    private Long maintenanceId;
    private LocalDate maintenanceDate;
    private int durationMinutes;
    private String partName;
    private int stockQuantity;
    private BigDecimal unitPrice;
    private LocalDate arrivalDate;
    private String supplierName;
    private String staffName;

    public MaintenanceRecordDetail(Long maintenanceId, LocalDate maintenanceDate, int durationMinutes,
                                   String partName, int stockQuantity, BigDecimal unitPrice,
                                   LocalDate arrivalDate, String supplierName, String staffName) {
        this.maintenanceId = maintenanceId;
        this.maintenanceDate = maintenanceDate;
        this.durationMinutes = durationMinutes;
        this.partName = partName;
        this.stockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
        this.arrivalDate = arrivalDate;
        this.supplierName = supplierName;
        this.staffName = staffName;
    }

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getPartName() {
        return partName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getStaffName() {
        return staffName;
    }
}
