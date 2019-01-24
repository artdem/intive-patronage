package com.example.intivepatronage.organization;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OrganizationsDTO {

    private Long id;

    @NotBlank(message = "Organizations name must not be blank.")
    @Size(min = 2, max = 20, message = "Organizations name must be between 2 and 20 characters.")
    private String organizationName;

    public OrganizationsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

}
