package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Role;
import com.ivan.pazar.domain.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role getByUserRole(UserRole userRole);
}
