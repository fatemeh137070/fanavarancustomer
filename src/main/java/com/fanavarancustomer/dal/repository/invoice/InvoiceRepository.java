package com.fanavarancustomer.dal.repository.invoice;

import com.fanavarancustomer.dal.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}