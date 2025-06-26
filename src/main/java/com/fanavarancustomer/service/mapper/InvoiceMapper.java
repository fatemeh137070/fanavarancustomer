//package com.fanavarancustomer.service.mapper;
//
//import com.fanavarancustomer.api.dto.invoice.InvoiceDto;
//import com.fanavarancustomer.dal.entity.Invoice;
//import org.mapstruct.*;
//
//@Mapper(componentModel = "spring")
//public interface InvoiceMapper {
//    @Mapping(source = "customerService.id", target = "customerServiceId")
//    InvoiceDto toDto(Invoice entity);
//
//    @Mapping(source = "customerServiceId", target = "customerService.id")
//    Invoice toEntity(InvoiceDto dto);
//}