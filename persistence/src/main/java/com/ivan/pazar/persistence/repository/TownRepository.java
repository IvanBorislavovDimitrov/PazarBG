package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownRepository extends JpaRepository<Town, String> {

    @Query(value = "select t from Town t join t.region r where r.name like %?1%")
    List<Town> findAllByRegionNameLike(String region);

    Town findByName(String name);
}
