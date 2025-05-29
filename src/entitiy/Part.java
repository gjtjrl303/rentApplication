package entitiy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Part {

    private Long id;
    private String partName;
    private BigDecimal unitPrice;
    private int stockQuantity; // SMALLINT UNSIGNED → Java에선 그냥 int
    private LocalDate arrivalDate;
    private String supplierName;

    public Part(Long id, String partName, BigDecimal unitPrice, int stockQuantity, LocalDate arrivalDate, String supplierName) {
        this.id = id;
        this.partName = partName;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
        this.arrivalDate = arrivalDate;
        this.supplierName = supplierName;
    }

    public Part(String partName, BigDecimal unitPrice, int stockQuantity, LocalDate arrivalDate, String supplierName) {
        this.partName = partName;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
        this.arrivalDate = arrivalDate;
        this.supplierName = supplierName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
