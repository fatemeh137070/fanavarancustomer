package com.fanavarancustomer.service.ticket;

import com.fanavarancustomer.api.dto.TicketDto;
import com.fanavarancustomer.api.dto.activityLogDto.ActivityLogDto;
import com.fanavarancustomer.dal.entity.Customer;
import com.fanavarancustomer.dal.entity.Ticket;
import com.fanavarancustomer.dal.repository.CustomerRepository;
import com.fanavarancustomer.dal.repository.TicketRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import com.fanavarancustomer.service.activityLogService.ActivityLogService;
import com.fanavarancustomer.service.eum.TicketStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class TicketManagerImpl implements TicketManager {


    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ActivityLogService activityLogService;

    public TicketManagerImpl(TicketRepository ticketRepository,
                             CustomerRepository customerRepository,
                             ModelMapper modelMapper,
                             ActivityLogService activityLogService) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.activityLogService = activityLogService;
    }

    @Override
    public TicketDto create(TicketDto dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // DTO → Entity
        Ticket ticket = modelMapper.map(dto, Ticket.class);
        ticket.setCustomer(customer);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());

        Ticket saved = ticketRepository.save(ticket);

        // ثبت لاگ ایجاد تیکت
        activityLogService.logAction(ActivityLogDto.builder()
                .userId(customer.getUser().getId())
                .action("CREATE_TICKET")
                .description("Created ticket with ID: " + saved.getId())
                .timestamp(LocalDateTime.now())
                .build());

        // Entity → DTO
        return modelMapper.map(saved, TicketDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketDto getById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        return modelMapper.map(ticket, TicketDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> getAll() {
        return ticketRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TicketDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void changeStatus(Long id, String status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        TicketStatus newStatus;
        try {
            newStatus = TicketStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }

        ticket.setStatus(newStatus);
        ticketRepository.save(ticket);
    }
}