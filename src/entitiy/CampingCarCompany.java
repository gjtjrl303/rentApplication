package entitiy;

public class CampingCarCompany {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String managerName;
    private String managerEmail;

    public CampingCarCompany(Long id, String name, String address, String phone, String managerName, String managerEmail) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.managerName = managerName;
        this.managerEmail = managerEmail;
    }

    public CampingCarCompany(String name, String address, String phone, String managerName, String managerEmail) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.managerName = managerName;
        this.managerEmail = managerEmail;
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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }
}
