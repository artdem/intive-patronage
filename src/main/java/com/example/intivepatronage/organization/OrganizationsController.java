package com.example.intivepatronage.organization;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/organizations")
class OrganizationsController {

    private final OrganizationsService organizationsService;

    OrganizationsController(OrganizationsService organizationsService) {
        this.organizationsService = organizationsService;
    }

    @GetMapping
    List<OrganizationsDTO> allOrganizations(){
        return organizationsService.allOrganizations();
    }

    @GetMapping("/{id}")
    OrganizationsDTO singleOrganization(@PathVariable Long id){
        return organizationsService.organizationById(id);
    }

    @PostMapping
    OrganizationsDTO newOrganization(@Valid @RequestBody OrganizationsDTO organizationDTO){
        return organizationsService.newOrganization(organizationDTO);
    }

    @PutMapping("/{id}")
    OrganizationsDTO updateOrganization(@Valid @RequestBody OrganizationsDTO updatedOrganizationDTO, @PathVariable Long id){
        return organizationsService.updateOrganization(updatedOrganizationDTO, id);
    }

    @DeleteMapping("/{id}")
    String deleteOrganization(@PathVariable Long id) {
        return organizationsService.deleteOrganization(id);
    }

}
