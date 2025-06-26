package com.fanavarancustomer.api.controller;

import com.fanavarancustomer.api.dto.TicketDto;
import com.fanavarancustomer.api.facade.TicketFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
//@RequiredArgsConstructor
public class TicketController {

    public TicketController(TicketFacade facade) {
        this.facade = facade;
    }

    private final TicketFacade facade;

    @PostMapping
    public ResponseEntity<TicketDto> create(@RequestBody TicketDto dto) {
        return ResponseEntity.ok(facade.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAll() {
        return ResponseEntity.ok(facade.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facade.getById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam String status) {
        facade.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}