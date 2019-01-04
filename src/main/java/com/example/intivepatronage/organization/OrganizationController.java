package com.example.intivepatronage.organization;

import com.example.intivepatronage.UniqueNameException;
import com.example.intivepatronage.conferenceRoom.ConferenceRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrganizationController {

    private final ConferenceRoomRepository conferenceRoomRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationController(ConferenceRoomRepository conferenceRoomRepository, OrganizationRepository organizationRepository) {
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.organizationRepository = organizationRepository;
    }

    @GetMapping("/organization")
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @GetMapping("/organization/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Long id) throws OrganizationNotFoundException {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
        return ResponseEntity.ok().body(organization);
    }

    @PostMapping("/organization")
    public ResponseEntity<Organization> addOrganization(@Valid @RequestBody Organization organization) throws UniqueNameException {
        if(!organizationRepository.existsOrganizationByOrganizationName(organization.getOrganizationName())) {
            organizationRepository.save(organization);
        } else {
            throw new UniqueNameException();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/organization/{id}")
    public ResponseEntity<Organization> updateOrganization(@Valid @RequestBody Organization updatedOrganization, @PathVariable Long id) throws OrganizationNotFoundException, UniqueNameException {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));

        organization.setOrganizationName(updatedOrganization.getOrganizationName());
        if(!organizationRepository.existsOrganizationByOrganizationName(updatedOrganization.getOrganizationName())) {
            organizationRepository.save(organization);
        } else {
            throw new UniqueNameException();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/organization/{id}")
    public void deleteOrganization(@PathVariable Long id) throws OrganizationNotFoundException {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
        organizationRepository.delete(organization);
    }

}
