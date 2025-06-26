package com.fanavarancustomer.service.roleManager;

import com.fanavarancustomer.api.dto.role.RoleDto;

import java.util.List;

public interface RoleManager {
    List<RoleDto> getAll();
    RoleDto create(RoleDto dto);
}