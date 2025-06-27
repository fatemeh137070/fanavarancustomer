package com.fanavarancustomer.dal.entity;

import com.fanavarancustomer.service.eum.InvoiceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate issuedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceType type; // PURCHASE or RENEWAL

    @Column(nullable = false)
    private boolean paid;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_service_id", nullable = false)
    private CustomerService customerService;

    //چند تا فاکتور فقط مربوط به یک مشتری هست
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}