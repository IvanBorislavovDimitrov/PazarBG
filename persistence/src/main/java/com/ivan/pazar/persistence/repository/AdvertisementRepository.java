package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, String> {

    List<Advertisement> findTop6ByActiveOrderByAddedOnDesc(boolean active);

    Page<Advertisement> findAllByCategoryNameLikeAndActive(String categoryName, Pageable pageable, boolean active);

    Page<Advertisement> findAllByActive(boolean active, Pageable pageable);

    Page<Advertisement> findAllByTitleLikeAndActiveIsTrue(String title, Pageable pageable);
}
