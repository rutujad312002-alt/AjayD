package com.arcitech.repository;

import com.arcitech.model.Project;
import com.arcitech.model.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByAssignedTeamId(Long teamId);
    List<Project> findByServiceType(ServiceType serviceType);
    List<Project> findByUsersId(Long userId);
}