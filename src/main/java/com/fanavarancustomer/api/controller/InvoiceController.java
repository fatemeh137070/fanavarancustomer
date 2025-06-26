package com.fanavarancustomer.api.controller;

import com.fanavarancustomer.api.dto.invoice.InvoiceDto;
import com.fanavarancustomer.api.facade.InvoiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
//@RequiredArgsConstructor
public class InvoiceController {

    public InvoiceController(InvoiceFacade facade) {
        this.facade = facade;
    }

    private final InvoiceFacade facade;

    @PostMapping
    public ResponseEntity<InvoiceDto> create(@RequestBody InvoiceDto dto) {
        return ResponseEntity.ok(facade.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facade.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAll() {
        return ResponseEntity.ok(facade.getAll());
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
        facade.markAsPaid(id);
        return ResponseEntity.noContent().build();
    }
}