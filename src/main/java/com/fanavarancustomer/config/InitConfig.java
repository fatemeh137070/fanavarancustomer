package com.fanavarancustomer.config;

import com.fanavarancustomer.dal.entity.Role;
import com.fanavarancustomer.dal.repository.role.RoleRepository;
import com.fanavarancustomer.service.eum.RoleName;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitConfig {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        Arrays.stream(RoleName.values()).forEach(roleName -> {
            if (roleRepository.findByName(roleName).isEmpty()) {
                roleRepository.save(Role.builder().name(roleName).build());
            }
        });
    }
}