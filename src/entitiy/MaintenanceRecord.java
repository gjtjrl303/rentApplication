package entitiy;

import java.time.LocalDate;

public class MaintenanceRecord {

    private Long id;
    private Long carId;
    private Long partId;
    private Long staffId;
    private LocalDate maintenanceDate;
    private int durationMinutes;

    public MaintenanceRecord(Long id, Long carId, Long partId, Long staffId, LocalDate maintenanceDate, int durationMinutes) {
        this.id = id;
        this.carId = carId;
        this.partId = partId;
        this.staffId = staffId;
        this.maintenanceDate = maintenanceDate;
        this.durationMinutes = durationMinutes;
    }

    public MaintenanceRecord(Long carId, Long partId, Long staffId, LocalDate maintenanceDate, int durationMinutes) {
        this.carId = carId;
        this.partId = partId;
        this.staffId = staffId;
        this.maintenanceDate = maintenanceDate;
        this.durationMinutes = durationMinutes;
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

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
