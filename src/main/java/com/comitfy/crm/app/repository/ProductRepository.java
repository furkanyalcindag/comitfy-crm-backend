package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.Currency;
import com.comitfy.crm.app.entity.Product;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product> {
}
