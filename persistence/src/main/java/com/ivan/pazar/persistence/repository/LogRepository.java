package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {

    void deleteAllByDateBefore(LocalDateTime localDateTime);
}
