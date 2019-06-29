package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {

    Region findByName(String name);

    @Query("select r from Region r join r.towns t where t.name = ?1")
    Region findByTownName(String townName);
}
