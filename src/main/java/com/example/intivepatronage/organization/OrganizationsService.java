package com.example.intivepatronage.organization;

import com.example.intivepatronage.UniqueNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationsService {

    private final OrganizationsRepository organizationsRepository;

    @Autowired
    public OrganizationsService(OrganizationsRepository organizationsRepository) {
        this.organizationsRepository = organizationsRepository;
    }

    public List<Organizations> allOrganizations() {
        return organizationsRepository.findAll();
    }

    public Organizations organizationById(Long id) throws OrganizationNotFoundException {
        Organizations organizationById = organizationsRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
        return organizationById;
    }

    public void newOrganization(Organizations organization) throws UniqueNameException {
        if (!organizationsRepository.existsOrganizationByOrganizationName(organization.getOrganizationName())) {
            organizationsRepository.save(organization);
        } else {
            throw new UniqueNameException();
        }
    }

    public void updateOrganization(Organizations updatedOrganization, Long id) throws UniqueNameException, OrganizationNotFoundException {
        Organizations organization = organizationsRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));

        if (!organizationsRepository.existsOrganizationByOrganizationName(updatedOrganization.getOrganizationName())) {
            organization.setOrganizationName(updatedOrganization.getOrganizationName());
            organizationsRepository.save(organization);
        } else {
            throw new UniqueNameException();
        }
    }

    public void deleteOrganization(Long id) {
        organizationsRepository.deleteById(id);
    }

}
