package entitiy;

import java.time.LocalDate;

public class Customer {

    private Long id;
    private String username;
    private String password;
    private String licenseNumber;
    private String name;
    private String address;
    private String phone;
    private String email;
    private LocalDate previousRentalDate;
    private String previousCarType;

    public Customer(Long id, String username, String password, String licenseNumber, String name, String address, String phone, String email, LocalDate previousRentalDate, String previousCarType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.previousRentalDate = previousRentalDate;
        this.previousCarType = previousCarType;
    }

    public Customer(String username, String password, String licenseNumber, String name, String address, String phone, String email, LocalDate previousRentalDate, String previousCarType) {
        this.username = username;
        this.password = password;
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.previousRentalDate = previousRentalDate;
        this.previousCarType = previousCarType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getPreviousRentalDate() {
        return previousRentalDate;
    }

    public void setPreviousRentalDate(LocalDate previousRentalDate) {
        this.previousRentalDate = previousRentalDate;
    }

    public String getPreviousCarType() {
        return previousCarType;
    }

    public void setPreviousCarType(String previousCarType) {
        this.previousCarType = previousCarType;
    }
}
