package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.ContactUsMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsMessageRepository extends JpaRepository<ContactUsMessage, String> {

}
