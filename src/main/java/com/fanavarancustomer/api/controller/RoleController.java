package com.fanavarancustomer.api.controller;

import com.fanavarancustomer.api.dto.role.RoleDto;
import com.fanavarancustomer.api.facade.RoleFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
//@RequiredArgsConstructor
public class RoleController {

    public RoleController(RoleFacade roleFacade) {
        this.roleFacade = roleFacade;
    }

    private final RoleFacade roleFacade;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAll() {
        return ResponseEntity.ok(roleFacade.getAll());
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto dto) {
        return ResponseEntity.ok(roleFacade.create(dto));
    }
}