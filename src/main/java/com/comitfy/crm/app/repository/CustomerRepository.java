package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.Currency;
import com.comitfy.crm.app.entity.Customer;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer> {
}
