package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {

}
