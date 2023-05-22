package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.CurrencyDTO;
import com.comitfy.crm.app.entity.City;
import com.comitfy.crm.app.entity.District;
import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.app.repository.CityRepository;
import com.comitfy.crm.app.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReferenceService {

    @Autowired
    CurrencyService currencyService;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DistrictRepository districtRepository;

    public List<ProposalStatusEnum> getProposalStatuses() {

        List<ProposalStatusEnum> list = Stream.of(ProposalStatusEnum.values())

                .collect(Collectors.toList());


        //list.remove(ProposalStatusEnum.APPROVED);

        return list;

    }

    public CurrencyDTO getDefaultCurrency() {
        return currencyService.activeCurrency();

    }

    public List<City> getCities() {
        return cityRepository.findAll();

    }

    public List<District> getDistricts(Integer cityId) {
        return districtRepository.findAllByCityId(cityId);

    }

    public List<OrderStatusEnum> getOrderStatuses() {

        List<OrderStatusEnum> list = Stream.of(OrderStatusEnum.values())

                .collect(Collectors.toList());


        //list.remove(ProposalStatusEnum.APPROVED);

        return list;

    }


}
