package com.arcitech.repository;

import com.arcitech.model.Service;
import com.arcitech.model.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByType(ServiceType type);
    List<Service> findByActive(boolean active);
    List<Service> findByServiceCategoryId(Long categoryId);
}