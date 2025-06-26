//package com.fanavarancustomer.service.mapper;
//
//import com.fanavarancustomer.api.dto.TicketDto;
//import com.fanavarancustomer.dal.entity.Ticket;
//import org.mapstruct.*;
//
//@Mapper(componentModel = "spring")
//public interface TicketMapper {
//    @Mapping(source = "customer.id", target = "customerId")
//    TicketDto toDto(Ticket entity);
//
//    @Mapping(source = "customerId", target = "customer.id")
//    Ticket toEntity(TicketDto dto);
//}