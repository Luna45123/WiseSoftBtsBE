package com.wisesoft.btsfare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisesoft.btsfare.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
    Optional<Role> findByName(Role.RoleType name);
}
