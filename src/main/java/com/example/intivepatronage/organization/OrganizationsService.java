package com.example.intivepatronage.organization;

import com.example.intivepatronage.exceptions.UniqueNameException;
import com.example.intivepatronage.exceptions.OrganizationNotFoundException;
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
        var organizationById = organizationsRepository.findById(id)
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

    public Organizations updateOrganization(Organizations updatedOrganization, Long id) throws UniqueNameException, OrganizationNotFoundException {
        if (!organizationsRepository.existsOrganizationByOrganizationName(updatedOrganization.getOrganizationName())) {
            return organizationsRepository.findById(id)
                    .map(organization -> {
                        organization.setOrganizationName(updatedOrganization.getOrganizationName());
                        return organizationsRepository.save(organization);
                    })
                    .orElseThrow(() -> new OrganizationNotFoundException(id));
        } else {
            throw new UniqueNameException();
        }
    }

    public void deleteOrganization(Long id) {
        organizationsRepository.deleteById(id);
    }

}
