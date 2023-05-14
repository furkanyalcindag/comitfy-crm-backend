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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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



    @Override
    @Transactional
    public CurrencyDTO save(CurrencyDTO requestDTO) {
        Currency entity = getMapper().dtoToEntity(requestDTO);
        if(entity.getIsDefault()){
            updateDefaultCurrency();
        }
        getRepository().save(entity);
        return getMapper().entityToDTO(entity);
    }

    @Override
    @Transactional
    public CurrencyDTO update(UUID id, CurrencyDTO dto) {

        Currency entity1 = getMapper().dtoToEntity(dto);
        if(entity1.getIsDefault()){
            updateDefaultCurrency();
        }
        Optional<Currency> entity = getRepository().findByUuid(id);

        if (entity.isPresent()) {

            getMapper().update(entity.get(), entity1);

            getRepository().save(entity.get());
            return dto;
        } else {
            entity.get();
            return null;
        }
    }

    public void updateDefaultCurrency(){
        List<Currency> currencyList = currencyRepository.findAll();
        for (Currency currency:currencyList) {
            currency.setIsDefault(Boolean.FALSE);
            currencyRepository.save(currency);
        }

    }


}
