package entitiy;

import java.math.BigDecimal;

public class Staff {

    private Long id;
    private String name;
    private String phone;
    private String address;
    private BigDecimal monthlySalary;
    private int numDependents;
    private String department;
    private Role role; // Enum 정의 필요

    public Staff(Long id, String name, String phone, String address, BigDecimal monthlySalary, int numDependents, String department, Role role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.monthlySalary = monthlySalary;
        this.numDependents = numDependents;
        this.department = department;
        this.role = role;
    }

    public Staff(String name, String phone, String address, BigDecimal monthlySalary, int numDependents, String department, Role role) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.monthlySalary = monthlySalary;
        this.numDependents = numDependents;
        this.department = department;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public int getNumDependents() {
        return numDependents;
    }

    public void setNumDependents(int numDependents) {
        this.numDependents = numDependents;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
