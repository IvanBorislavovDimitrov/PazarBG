package com.ivan.pazar.domain.model.entity;

import com.ivan.pazar.domain.model.enums.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table
@Entity(name = "roles")
public class Role extends IdEntity {

    @Column(name = "name", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ManyToMany(mappedBy = "roles", targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> users;

    public Role() {
        users = new HashSet<>();
    }

    public Role(UserRole userRole) {
        this();
        this.userRole = userRole;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
