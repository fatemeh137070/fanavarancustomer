package com.fanavarancustomer.api.dto.invoice;

import com.fanavarancustomer.service.eum.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {
    private Long id;
    private BigDecimal amount;
    private LocalDate issuedDate;
    private InvoiceType type;
    private boolean paid;
    private Long customerServiceId;

}