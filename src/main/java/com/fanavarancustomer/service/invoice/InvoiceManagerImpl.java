package com.fanavarancustomer.service.invoice;

import com.fanavarancustomer.api.dto.invoice.InvoiceDto;
import com.fanavarancustomer.dal.entity.CustomerService;
import com.fanavarancustomer.dal.entity.Invoice;
import com.fanavarancustomer.dal.repository.customerService.CustomerServiceRepository;
import com.fanavarancustomer.dal.repository.invoice.InvoiceRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class InvoiceManagerImpl implements InvoiceManager {


    private final InvoiceRepository invoiceRepository;
    private final CustomerServiceRepository customerServiceRepository;
    private final ModelMapper modelMapper;

    public InvoiceManagerImpl(InvoiceRepository invoiceRepository,
                              CustomerServiceRepository customerServiceRepository,
                              ModelMapper modelMapper) {
        this.invoiceRepository = invoiceRepository;
        this.customerServiceRepository = customerServiceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public InvoiceDto create(InvoiceDto dto) {
        CustomerService service = customerServiceRepository.findById(dto.getCustomerServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Customer service not found"));

        Invoice invoice = modelMapper.map(dto, Invoice.class);
        invoice.setCustomerService(service);
        invoice.setPaid(false);

        Invoice saved = invoiceRepository.save(invoice);
        return modelMapper.map(saved, InvoiceDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceDto getById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));
        return modelMapper.map(invoice, InvoiceDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceDto> getAll() {
        return invoiceRepository.findAll().stream()
                .map(inv -> modelMapper.map(inv, InvoiceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void markAsPaid(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));
        invoice.setPaid(true);
        invoiceRepository.save(invoice);
    }
}