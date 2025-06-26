package com.fanavarancustomer.dal.repository;

import com.fanavarancustomer.dal.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByNationalIdOrCompanyRegNo(String nationalId, String companyRegNo); // ✅ درست
}