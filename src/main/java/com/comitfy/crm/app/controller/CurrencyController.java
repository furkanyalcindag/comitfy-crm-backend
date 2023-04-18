package com.comitfy.crm.app.controller;

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
import com.comitfy.crm.app.service.CurrencyService;
import com.comitfy.crm.app.service.SettingsService;
import com.comitfy.crm.app.specification.CurrencySpecification;
import com.comitfy.crm.app.specification.SettingsSpecification;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.util.common.BaseCrudController;
import com.comitfy.crm.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("currency")
public class CurrencyController extends BaseCrudController<CurrencyDTO, CurrencyRequestDTO, Currency, CurrencyRepository, CurrencyMapper, CurrencySpecification, CurrencyService> {

    @Autowired
    CurrencyMapper currencyMapper;

    @Autowired
    CurrencyService currencyService;


    @Override
    protected CurrencyService getService() {
        return currencyService;
    }

    @Override
    protected CurrencyMapper getMapper() {
        return currencyMapper;
    }


}
