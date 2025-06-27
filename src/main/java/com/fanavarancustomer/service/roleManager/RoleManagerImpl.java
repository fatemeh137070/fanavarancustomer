package com.fanavarancustomer.service.roleManager;

import com.fanavarancustomer.api.dto.role.RoleDto;
import com.fanavarancustomer.dal.entity.Role;
import com.fanavarancustomer.dal.repository.role.RoleRepository;
import com.fanavarancustomer.service.eum.RoleName;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
//@RequiredArgsConstructor
public class RoleManagerImpl implements RoleManager {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleManagerImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto create(RoleDto dto) {
        Role role = Role.builder()
                .name(RoleName.valueOf(dto.getName().name()))
                .build();

        Role saved = roleRepository.save(role);
        return modelMapper.map(saved, RoleDto.class);
    }
}