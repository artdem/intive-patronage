package com.example.intivepatronage.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrganizationsController {

    private final OrganizationsService organizationsService;

    @Autowired
    public OrganizationsController(OrganizationsService organizationsService) {
        this.organizationsService = organizationsService;
    }

    @GetMapping("/organizations")
    public List<Organizations> allOrganizations(){
        return organizationsService.allOrganizations();
    }

    @GetMapping("/organizations/{id}")
    public ResponseEntity<Organizations> singleOrganization(@PathVariable Long id){
        var organization = organizationsService.organizationById(id);
        if (organization != null){
            return ResponseEntity.ok(organization);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/organizations")
    public ResponseEntity<Organizations> newOrganization(@Valid @RequestBody Organizations organization){
        organizationsService.newOrganization(organization);
        return ResponseEntity.ok().body(organization);
    }

    @PutMapping("/organizations/{id}")
    public ResponseEntity<Organizations> updateOrganization(@PathVariable Long id, @Valid @RequestBody Organizations updatedOrganization){
        var organization = organizationsService.updateOrganization(updatedOrganization, id);
        return ResponseEntity.ok().body(organization);
    }

    @DeleteMapping("/organizations/{id}")
    public void deleteOrganization(@PathVariable Long id){
        organizationsService.deleteOrganization(id);
    }

}
