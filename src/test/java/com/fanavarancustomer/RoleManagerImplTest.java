package com.fanavarancustomer;

import com.fanavarancustomer.api.dto.role.RoleDto;
import com.fanavarancustomer.dal.entity.Role;
import com.fanavarancustomer.dal.repository.role.RoleRepository;
import com.fanavarancustomer.service.eum.RoleName;
import com.fanavarancustomer.service.roleManager.RoleManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleManagerImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoleManagerImpl roleManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // roleManager با roleRepository و modelMapper مقداردهی می‌شود
    }

    @Test
    void testGetAllRoles() {
        // Given
        List<Role> roles = List.of(
                Role.builder().id(1L).name(RoleName.ADMIN).build(),
                Role.builder().id(2L).name(RoleName.CUSTOMER).build()
        );
        List<RoleDto> dtos = List.of(
                RoleDto.builder().id(1L).name("ADMIN").build(),
                RoleDto.builder().id(2L).name("CUSTOMER").build()
        );

        when(roleRepository.findAll()).thenReturn(roles);
        // شبیه‌سازی نگاشت هر Role به RoleDto
        when(modelMapper.map(roles.get(0), RoleDto.class)).thenReturn(dtos.get(0));
        when(modelMapper.map(roles.get(1), RoleDto.class)).thenReturn(dtos.get(1));

        // When
        List<RoleDto> result = roleManager.getAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getName());
        assertEquals("CUSTOMER", result.get(1).getName());
        verify(roleRepository).findAll();
        verify(modelMapper).map(roles.get(0), RoleDto.class);
        verify(modelMapper).map(roles.get(1), RoleDto.class);
    }

    @Test
    void testCreateRole() {
        // Given
        RoleDto dto = RoleDto.builder().name("SUPPORT").build();
        Role savedEntity = Role.builder().id(3L).name(RoleName.SUPPORT).build();
        RoleDto returnedDto = RoleDto.builder().id(3L).name("SUPPORT").build();

        when(roleRepository.save(any(Role.class))).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, RoleDto.class)).thenReturn(returnedDto);

        // When
        RoleDto result = roleManager.create(dto);

        // Then
        assertNotNull(result.getId());
        assertEquals("SUPPORT", result.getName());
        verify(roleRepository).save(any(Role.class));
        verify(modelMapper).map(savedEntity, RoleDto.class);
    }
}