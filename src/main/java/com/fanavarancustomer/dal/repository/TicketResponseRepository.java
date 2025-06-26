package com.fanavarancustomer.dal.repository;

import com.fanavarancustomer.dal.entity.TicketResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketResponseRepository extends JpaRepository<TicketResponse, Long> {

    // بازیابی تمام پاسخ‌های مربوط به یک تیکت خاص
    List<TicketResponse> findByTicketId(Long ticketId);
}