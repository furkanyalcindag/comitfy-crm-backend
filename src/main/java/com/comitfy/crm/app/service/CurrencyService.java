package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.CurrencyDTO;
import com.comitfy.crm.app.dto.SettingsDTO;
import com.comitfy.crm.app.dto.requestDTO.CurrencyRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.crm.app.entity.Currency;
import com.comitfy.crm.app.entity.Settings;
import com.comitfy.crm.app.mapper.CurrencyMapper;
import com.comitfy.crm.app.mapper.SettingsMapper;
import com.comitfy.crm.app.repository.CurrencyRepository;
import com.comitfy.crm.app.repository.SettingsRepository;
import com.comitfy.crm.app.specification.CurrencySpecification;
import com.comitfy.crm.app.specification.SettingsSpecification;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.userModule.repository.UserRepository;
import com.comitfy.crm.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CurrencyService extends BaseService<CurrencyDTO, CurrencyRequestDTO, Currency, CurrencyRepository, CurrencyMapper, CurrencySpecification> {

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    CurrencyMapper currencyMapper;

    @Autowired
    CurrencySpecification currencySpecification;

    @Autowired
    UserRepository userRepository;

    @Override
    public CurrencyRepository getRepository() {
        return currencyRepository;
    }

    @Override
    public CurrencyMapper getMapper() {
        return currencyMapper;
    }

    @Override
    public CurrencySpecification getSpecification() {
        return currencySpecification;
    }


}