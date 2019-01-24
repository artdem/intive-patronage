package com.example.intivepatronage.organization;

import com.example.intivepatronage.exceptions.UniqueNameException;
import com.example.intivepatronage.exceptions.OrganizationNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
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
        var organizationId = convertToDto(organizationsRepository.findByOrganizationName(organizationName));
        if (organizationsRepository.existsOrganizationByOrganizationName(organizationName) && !organizationId.getId().equals(id)) {
            throw new UniqueNameException();
        }
        Organizations organizationToUpdate = organizationsRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
        organizationToUpdate.setOrganizationName(organizationUpdate.getOrganizationName());
        return convertToDto(organizationsRepository.save(organizationToUpdate));
    }

    String deleteOrganization(Long id) throws OrganizationNotFoundException {
        organizationsRepository.deleteById(id);
        return "Organization no. " + id + " successfully deleted.";
    }

    private OrganizationsDTO convertToDto(Organizations organizations) {
        return objectMapper.convertValue(organizations, OrganizationsDTO.class);
    }

    private Organizations convertToEntity(OrganizationsDTO organizationsDTO) {
        return objectMapper.convertValue(organizationsDTO, Organizations.class);
    }

}
