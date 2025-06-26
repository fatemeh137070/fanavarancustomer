package com.fanavarancustomer.api.controller;

import com.fanavarancustomer.api.dto.CustomerDto;
import com.fanavarancustomer.api.facade.CustomerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
//@RequiredArgsConstructor
public class CustomerController {

    public CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    private final CustomerFacade customerFacade;

    @PostMapping
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerDto dto) {
        CustomerDto saved = customerFacade.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerFacade.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok(customerFacade.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable Long id, @RequestBody CustomerDto dto) {
        return ResponseEntity.ok(customerFacade.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}