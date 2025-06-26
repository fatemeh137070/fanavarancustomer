package com.fanavarancustomer.dal.repository.role;

import com.fanavarancustomer.dal.entity.Role;
import com.fanavarancustomer.service.eum.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}