package com.fanavarancustomer.dal.repository;

import com.fanavarancustomer.dal.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}