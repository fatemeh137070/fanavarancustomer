package com.fanavarancustomer.api.facade;

import com.fanavarancustomer.api.dto.invoice.InvoiceDto;
import com.fanavarancustomer.service.invoice.InvoiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@RequiredArgsConstructor
public class InvoiceFacade {
    public InvoiceFacade(InvoiceManager manager) {
        this.manager = manager;
    }

    private final InvoiceManager manager;

    public InvoiceDto create(InvoiceDto dto) {
        return manager.create(dto);
    }

    public InvoiceDto getById(Long id) {
        return manager.getById(id);
    }

    public List<InvoiceDto> getAll() {
        return manager.getAll();
    }

    public void markAsPaid(Long id) {
        manager.markAsPaid(id);
    }
}