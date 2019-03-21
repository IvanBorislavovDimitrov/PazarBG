package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, String> {

    List<Advertisement> findTop6ByOrderByAddedOnDesc();

}
