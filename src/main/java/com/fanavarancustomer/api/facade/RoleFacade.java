package com.fanavarancustomer.api.facade;

import com.fanavarancustomer.api.dto.role.RoleDto;
import com.fanavarancustomer.service.roleManager.RoleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@RequiredArgsConstructor
public class RoleFacade {

    public RoleFacade(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    private final RoleManager roleManager;

    public List<RoleDto> getAll() {
        return roleManager.getAll();
    }

    public RoleDto create(RoleDto dto) {
        return roleManager.create(dto);
    }
}