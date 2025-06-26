package com.fanavarancustomer.service.invoice;

import com.fanavarancustomer.api.dto.invoice.InvoiceDto;

import java.util.List;

public interface InvoiceManager {
    InvoiceDto create(InvoiceDto dto);
    InvoiceDto getById(Long id);
    List<InvoiceDto> getAll();
    void markAsPaid(Long id);
}