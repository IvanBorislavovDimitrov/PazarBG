package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByUsername(String username);

    List<User> findAllByUsernameContaining(String prefix);

    Page<User> findAll(Pageable pageable);

    void deleteAllByActiveIsFalse();
}
