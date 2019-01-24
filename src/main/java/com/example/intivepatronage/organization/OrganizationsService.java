package com.example.intivepatronage.organization;

import com.example.intivepatronage.exceptions.UniqueNameException;
import com.example.intivepatronage.exceptions.OrganizationNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationsService {

    private final OrganizationsRepository organizationsRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrganizationsService(OrganizationsRepository organizationsRepository, ObjectMapper objectMapper) {
        this.organizationsRepository = organizationsRepository;
        this.objectMapper = objectMapper;
    }

    List<OrganizationsDTO> allOrganizations() {
        return organizationsRepository.findAll().stream()
                .map(organizations -> convertToDto(organizations))
                .collect(Collectors.toList());
    }

    OrganizationsDTO organizationById(Long id) throws OrganizationNotFoundException {
        var organizationById = organizationsRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
        return convertToDto(organizationById);
    }

    OrganizationsDTO newOrganization(OrganizationsDTO newOrganization) throws UniqueNameException {
        if (organizationsRepository.existsOrganizationByOrganizationName(newOrganization.getOrganizationName())) {
            throw new UniqueNameException();
        }
        return convertToDto(organizationsRepository.save(convertToEntity(newOrganization)));
    }

    OrganizationsDTO updateOrganization(OrganizationsDTO organizationUpdate, Long id) throws UniqueNameException, OrganizationNotFoundException {
        var organizationName = organizationUpdate.getOrganizationName();
        var organizationId = organizationsRepository.findByOrganizationName(organizationName).getId();
        if (organizationsRepository.existsOrganizationByOrganizationName(organizationName) && !id.equals(organizationId)) {
            throw new UniqueNameException();
        }
        var organizationToUpdate = convertToEntity(convertToDto(organizationsRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id))));
        organizationToUpdate.setOrganizationName(organizationUpdate.getOrganizationName());
        return convertToDto(organizationsRepository.save(organizationToUpdate));
    }

    void deleteOrganization(Long id) {
        organizationsRepository.deleteById(id);
    }

    private OrganizationsDTO convertToDto(Organizations organizations) {
        return objectMapper.convertValue(organizations, OrganizationsDTO.class);
    }

    private Organizations convertToEntity(OrganizationsDTO organizationsDTO) {
        return objectMapper.convertValue(organizationsDTO, Organizations.class);
    }

}
