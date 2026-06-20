package com.csc3402.security.parttimer.dto;

public class CompanyDTO {
    private Long companyId;
    private String name;
    private String email;
    private String location;

    public CompanyDTO() {}

    public CompanyDTO(Long companyId, String name, String email, String location) {
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.location = location;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
