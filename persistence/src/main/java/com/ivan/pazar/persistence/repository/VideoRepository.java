package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    void deleteByName(String name);
}
