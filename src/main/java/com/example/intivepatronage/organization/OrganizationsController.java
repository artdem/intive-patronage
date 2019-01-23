package com.example.intivepatronage.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/organizations")
class OrganizationsController {

    private final OrganizationsService organizationsService;

    @Autowired
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
        //return organizationDTO;
    }

    @PostMapping
    ResponseEntity<OrganizationsDTO> newOrganization(@Valid @RequestBody OrganizationsDTO organizationDTO){
        return ResponseEntity.ok().body(organizationsService.newOrganization(organizationDTO));
    }

    @PutMapping("/{id}")
    ResponseEntity<OrganizationsDTO> updateOrganization(@PathVariable Long id, @Valid @RequestBody OrganizationsDTO updatedOrganizationDTO){
        return ResponseEntity.ok().body(organizationsService.updateOrganization(updatedOrganizationDTO, id));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    void deleteOrganization(@PathVariable Long id){
        organizationsService.deleteOrganization(id);
    }

}
