package com.fanavarancustomer.service.ticketResponseService;

import com.fanavarancustomer.api.dto.ticketResponseDto.TicketResponseDto;
import com.fanavarancustomer.dal.entity.Ticket;
import com.fanavarancustomer.dal.entity.TicketResponse;
import com.fanavarancustomer.dal.entity.User;
import com.fanavarancustomer.dal.repository.TicketRepository;
import com.fanavarancustomer.dal.repository.TicketResponseRepository;
import com.fanavarancustomer.dal.repository.userRepository.UserRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
//@RequiredArgsConstructor
public class TicketResponseServiceImpl implements TicketResponseService {

    private final TicketResponseRepository ticketResponseRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public TicketResponseServiceImpl(TicketResponseRepository ticketResponseRepository,
                                     TicketRepository ticketRepository,
                                     UserRepository userRepository,
                                     ModelMapper modelMapper) {
        this.ticketResponseRepository = ticketResponseRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TicketResponseDto respondToTicket(TicketResponseDto dto) {
        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        User user = userRepository.findById(dto.getResponderId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        TicketResponse response = modelMapper.map(dto, TicketResponse.class);
        response.setRespondedAt(LocalDateTime.now());
        response.setTicket(ticket);
        response.setResponder(user);

        TicketResponse saved = ticketResponseRepository.save(response);
        return modelMapper.map(saved, TicketResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDto> getResponsesForTicket(Long ticketId) {
        return ticketResponseRepository.findByTicketId(ticketId).stream()
                .map(response -> modelMapper.map(response, TicketResponseDto.class))
                .collect(Collectors.toList());
    }
}