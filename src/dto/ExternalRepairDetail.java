package dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExternalRepairDetail {

    private Long repairId;
    private LocalDate repairDate;
    private BigDecimal repairCost;
    private String shopName;
    private String address;
    private String phone;

    public ExternalRepairDetail(Long repairId, LocalDate repairDate, BigDecimal repairCost,
                                String shopName, String address, String phone) {
        this.repairId = repairId;
        this.repairDate = repairDate;
        this.repairCost = repairCost;
        this.shopName = shopName;
        this.address = address;
        this.phone = phone;
    }

    public Long getRepairId() {
        return repairId;
    }

    public LocalDate getRepairDate() {
        return repairDate;
    }

    public BigDecimal getRepairCost() {
        return repairCost;
    }

    public String getShopName() {
        return shopName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
