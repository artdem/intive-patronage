package com.example.intivepatronage.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationsRepository extends JpaRepository<Organizations, Long> {

    boolean existsOrganizationByOrganizationName(String name);

    Organizations findByOrganizationName(String name);

}
