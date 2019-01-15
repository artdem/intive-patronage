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

    @GetMapping("/organization")
    public List<Organizations> allOrganizations(){
        return organizationsService.allOrganizations();
    }

    @GetMapping("/organization/{id}")
    public ResponseEntity<Organizations> singleOrganization(@PathVariable Long id){
        Organizations organization = organizationsService.organizationById(id);
        if (organization != null){
            return ResponseEntity.ok(organization);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/organization")
    public ResponseEntity<Organizations> newOrganization(@Valid @RequestBody Organizations organization){
        organizationsService.newOrganization(organization);
        return ResponseEntity.ok().body(organization);
    }

    @PutMapping("/organization/{id}")
    public ResponseEntity<Organizations> updateOrganization(@PathVariable Long id, @Valid @RequestBody Organizations updatedOrganization){
        organizationsService.updateOrganization(updatedOrganization, id);
        return ResponseEntity.ok().body(updatedOrganization);
    }

    @DeleteMapping("/organization/{id}")
    public void deleteOrganization(@PathVariable Long id){
        organizationsService.deleteOrganization(id);
    }

}
