package com.fanavarancustomer.api.controller;

import com.fanavarancustomer.api.dto.customerService.CustomerServiceDto;
import com.fanavarancustomer.api.facade.CustomerServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/customer-services")
//@RequiredArgsConstructor
public class CustomerServiceController {

    public CustomerServiceController(CustomerServiceFacade facade) {
        this.facade = facade;
    }

    private final CustomerServiceFacade facade;

    @PostMapping
    public ResponseEntity<CustomerServiceDto> create(@RequestBody CustomerServiceDto dto) {
        return ResponseEntity.ok(facade.create(dto));
    }


    @GetMapping
    public ResponseEntity<Page<CustomerServiceDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(facade.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerServiceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facade.getById(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        facade.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}