//package com.fanavarancustomer.service.mapper;
//
//import com.fanavarancustomer.api.dto.customerService.CustomerServiceDto;
//import com.fanavarancustomer.dal.entity.CustomerService;
//import org.mapstruct.*;
//
//@Mapper(componentModel = "spring")
//public interface CustomerServiceMapper {
//    @Mapping(source = "customer.id", target = "customerId")
//    CustomerServiceDto toDto(CustomerService service);
//
//    @Mapping(source = "customerId", target = "customer.id")
//    CustomerService toEntity(CustomerServiceDto dto);
//}