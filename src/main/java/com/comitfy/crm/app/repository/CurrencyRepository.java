package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.Currency;
import com.comitfy.crm.app.entity.Settings;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends BaseRepository<Currency> {
}
