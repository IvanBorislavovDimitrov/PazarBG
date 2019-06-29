package com.ivan.pazar.persistence.service.service_api;

import com.ivan.pazar.domain.model.entity.Role;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.service.api.RoleService;

public interface RoleServiceExtended extends RoleService {
    Role getByUserRole(UserRole userRole);
}
