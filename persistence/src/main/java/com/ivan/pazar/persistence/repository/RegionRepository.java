package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {

    Region findByName(String name);
}
