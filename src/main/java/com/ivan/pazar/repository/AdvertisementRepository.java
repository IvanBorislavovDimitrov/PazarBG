package com.ivan.pazar.repository;

import com.ivan.pazar.model.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, String> {
}
